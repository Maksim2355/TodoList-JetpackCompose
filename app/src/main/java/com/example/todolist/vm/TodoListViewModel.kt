package com.example.todolist.vm


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.common.FilterInteractor
import com.example.todolist.common.FilterParams
import com.example.todolist.data.model.Task
import com.example.todolist.data.repository.TaskRepository

class TodoListViewModel(
    private val repository: TaskRepository,
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    val taskList: LiveData<List<Task>> = repository.observedTask

    fun updateTask(task: Task) {
        repository.updateTask(task)
    }

    fun addNewTask(task: Task) {
        repository.addNewTask(task)
    }

    fun removeTask(task: Task) {
        repository.removeTask(task)
    }

    fun filterTaskByQuery(query: String) {
        filterInteractor.filterByQuery(query)
    }

    fun sortedTaskByParams(params: FilterParams){
        filterInteractor.filterByDate(params)
    }
}