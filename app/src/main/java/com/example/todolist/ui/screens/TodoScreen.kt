package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.model.Task


@Composable
fun TodoScreen(
    tasks: LiveData<List<Task>>,
    onAddTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit
) {
    val taskList by tasks.observeAsState(initial = emptyList())
    println(taskList)
    Scaffold(
        topBar = {
            TodoAppBar()
        },
        floatingActionButton = {
            TodoFloatingActionButton()
        }
    ) {
        BodyContent(
            taskList,
            onRemoveTask,
            onUpdateTask,
        )
    }
}

@Composable
fun TodoAppBar(modifier: Modifier = Modifier) {
    TopAppBar(title = { Text("Todo") }, modifier = modifier)
}

@Composable
fun TodoFloatingActionButton(modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { },
        backgroundColor = Color.Blue,
        contentColor = Color.White,
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Filled.Add)
    }
}

@Composable
fun BodyContent(
    tasks: List<Task>,
    onRemoveTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit,
) {
    println(tasks.size)
    println(tasks)
    if (tasks.isEmpty()) {
        NoDataFeed("No data", modifier = Modifier)
    } else {
        TaskFeed(tasks, onUpdateTask, onRemoveTask)
    }
}

@Composable
fun NoDataFeed(message: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            Text(
                message,
                fontSize = TextUnit.Companion.Sp(48),
                modifier = Modifier.padding(vertical = 4.dp),
                fontWeight = FontWeight.Bold
            )
            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun TaskFeed(
    tasks: List<Task>,
    onUpdateTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentPosition: MutableState<Int?> = remember { mutableStateOf(null) }
    LazyColumn(
        modifier = modifier
    ) {
        items(tasks) { task ->
            if (task.id == currentPosition.value) {
                TodoItemEditing(
                    task = task,
                    changeCurrentPosition = { currentPosition.value = null },
                    onUpdateTask = onUpdateTask,
                    onRemoveTask = onRemoveTask,
                    modifier = Modifier.padding(8.dp).fillMaxWidth()
                )
            } else {
                TodoItem(
                    task = task,
                    changeCurrentPosition = { currentPosition.value = task.id },
                    modifier = Modifier.padding(8.dp).fillMaxWidth()
                )

            }
        }
    }
}


@Preview
@Composable
fun PreviewTodoScreen() {
    TodoScreen(
        MutableLiveData(emptyList()), {}, {}, {})
}