package com.example.todolist.data.db

import com.example.todolist.data.model.Task

interface TaskDao {

    fun getAllTask(): List<Task>

    fun insertTask(task: Task)

    fun deleteTask(task: Task)

    fun updateTask(task: Task)

}