package com.example.todolist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.common.SearchInteractor
import com.example.todolist.data.db.TaskDao
import com.example.todolist.data.model.Task

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository, SearchInteractor {

    private val _taskStreams: MutableLiveData<List<Task>> = MutableLiveData(taskDao.getAllTask())

    override val observedTask: LiveData<List<Task>>
        get() = _taskStreams

    override fun updateTask(task: Task) {
        taskDao.updateTask(task)
        _taskStreams.postValue(taskDao.getAllTask())
    }

    override fun addNewTask(task: Task) {
        taskDao.insertTask(task)
        _taskStreams.postValue(taskDao.getAllTask())
    }

    override fun removeTask(task: Task) {
        taskDao.deleteTask(task)
        _taskStreams.postValue(taskDao.getAllTask())
    }

    override fun searchTask(query: String) {
        val tasks = taskDao.getAllTask().filter { it.name.startsWith(query, true) }
        _taskStreams.postValue(tasks)
    }
}