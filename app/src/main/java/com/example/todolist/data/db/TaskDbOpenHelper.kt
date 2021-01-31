package com.example.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist.data.model.StatusTask
import com.example.todolist.data.model.Task

class TaskDbOpenHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), TaskDao {

    override fun getAllTask(): List<Task> {
        val c = readableDatabase.query(
            TABLE_TASK,
            null, null, null, null, null, null
        )
        val listTask = mutableListOf<Task>()
        c.moveToFirst()
        while (!c.isAfterLast) {
            val taskId = c.getInt(c.getColumnIndex(TASK_ID_COLUMN))
            val taskName = c.getString(c.getColumnIndex(TASK_NAME_COLUMN))
            val taskDescription = c.getString(c.getColumnIndex(TASK_DESCRIPTION_COLUMN))
            val taskStatus: StatusTask = when (c.getInt(c.getColumnIndex(TASK_STATUS_COLUMN))) {
                0 -> StatusTask.InQueue
                1 -> StatusTask.InProgress
                else -> StatusTask.Completed
            }
            listTask.add(Task(taskId, taskName, taskDescription, taskStatus))
            c.moveToNext()
        }
        c.close()
        return listTask
    }

    override fun insertTask(task: Task) {
        writableDatabase.insert(TABLE_TASK, null, convertTaskToContentValues(task))
    }

    override fun deleteTask(task: Task) {
        writableDatabase.delete(TABLE_TASK, "id = ${task.id}", null)
    }

    override fun updateTask(task: Task) {
        writableDatabase.update(
            TABLE_TASK,
            convertTaskToContentValues(task),
            "id = ${task.id}",
            null
        )
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "create table $TABLE_TASK (" +
                    "$TASK_ID_COLUMN integer primary key autoincrement," +
                    "$TASK_NAME_COLUMN text," +
                    "$TASK_DESCRIPTION_COLUMN text," +
                    "$TASK_STATUS_COLUMN integer);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val DB_NAME = "TodoList.db"
        private const val DB_VERSION = 1
        private const val TABLE_TASK = "Tasks"
        private const val TASK_ID_COLUMN = "id"
        private const val TASK_NAME_COLUMN = "task_name"
        private const val TASK_DESCRIPTION_COLUMN = "task_description"
        private const val TASK_STATUS_COLUMN = "status"
        private const val STATUS_IN_QUEUE = 0
        private const val STATUS_IN_PROGRESS = 1
        private const val STATUS_COMPLETED = 2
    }

    private fun convertTaskToContentValues(task: Task): ContentValues {
        val statusTask: Int = when (task.status) {
            StatusTask.InQueue -> STATUS_IN_QUEUE
            StatusTask.InProgress -> STATUS_IN_PROGRESS
            StatusTask.Completed -> STATUS_COMPLETED
        }
        return ContentValues().apply {
            put(TASK_NAME_COLUMN, task.name)
            put(TASK_DESCRIPTION_COLUMN, task.description)
            put(TASK_STATUS_COLUMN, statusTask)
        }
    }

}