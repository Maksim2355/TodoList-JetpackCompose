package com.example.todolist.data.repository

import androidx.lifecycle.LiveData
import com.example.todolist.data.model.Task

interface TaskRepository {

    fun observeTask(): LiveData<List<Task>>

    fun updateTask(task: Task)

    fun addNewTask(task: Task)

    fun removeTask(task: Task)

}