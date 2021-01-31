package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.repository.TaskRepositoryImpl
import com.example.todolist.ui.TodoListTheme
import com.example.todolist.ui.screens.TodoScreen
import com.example.todolist.vm.TodoListViewModel
import com.example.todolist.vm.TodoViewModelFactory

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val todoViewModel = ViewModelProvider(
            this,
            TodoViewModelFactory(TaskRepositoryImpl(App.dao))
        ).get(TodoListViewModel::class.java)
        setContent {
            App {
                val taskList by todoViewModel.taskList.observeAsState(initial = emptyList())
                println(taskList)
                TodoScreen(
                    taskList,
                    todoViewModel::addNewTask,
                    todoViewModel::removeTask,
                    todoViewModel::updateTask
                )
            }
        }
    }
}

@Composable
fun App(content: @Composable () -> Unit) {
    TodoListTheme {
        content()
    }
}