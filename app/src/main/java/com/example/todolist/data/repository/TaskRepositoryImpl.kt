package com.example.todolist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.common.FilterInteractor
import com.example.todolist.common.FilterParams
import com.example.todolist.data.db.TaskDao
import com.example.todolist.data.model.Task

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository, FilterInteractor {

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

    override fun filterByQuery(query: String) {
        val tasks = taskDao.getAllTask().filter { it.name.startsWith(query, true) }
        _taskStreams.postValue(tasks)
    }

    override fun filterByDate(params: FilterParams) {
        val tasks = when (params) {
            FilterParams.Ascending -> taskDao.getAllTask().sortedBy { it.createdDate }
            FilterParams.Descending -> taskDao.getAllTask().sortedByDescending { it.createdDate }
        }
        _taskStreams.postValue(tasks)
    }
}