package com.example.todolist.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.common.FilterInteractor
import com.example.todolist.data.repository.TaskRepository

class TodoViewModelFactory(
    private val repository: TaskRepository,
    private val filterInteractor: FilterInteractor
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoListViewModel(repository, filterInteractor) as T
    }

}