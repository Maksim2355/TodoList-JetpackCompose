package com.example.todolist.vm


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.model.Task
import com.example.todolist.data.repository.TaskRepository

class TodoListViewModel(private val repository: TaskRepository): ViewModel(){

    private val _isShowAddTaskDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val isShowAddTaskDialog: LiveData<Boolean>
        get() = _isShowAddTaskDialog

    private val _currentEditPosition: MutableLiveData<Int?> = MutableLiveData()
    val currentEditPosition: LiveData<Int?>
        get() = _currentEditPosition

    val taskList: LiveData<List<Task>> = repository.observeTask()

    fun changeCurrentPosition(position: Int?){
        _currentEditPosition.postValue(position)
    }

    fun updateTask(task: Task){
        repository.updateTask(task)
    }

    fun addNewTask(task: Task){
        repository.addNewTask(task)
    }

    fun removeTask(task: Task){
        repository.removeTask(task)
    }
}