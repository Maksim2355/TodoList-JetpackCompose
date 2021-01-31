package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.todolist.data.model.Task


@Composable
fun TodoScreen(
    tasks: List<Task>,
    onAddTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit
) {
    val isShowAddTaskDialog = remember {
        mutableStateOf(false)
    }
    val isShowEditDialog = remember {
        mutableStateOf(false)
    }
    val taskByDefault: MutableState<Task?> = remember {
        mutableStateOf(null)
    }
    Scaffold(
        topBar = {
            TodoAppBar()
        },
        floatingActionButton = {
            TodoFloatingActionButton(onShowDialog = { isShowAddTaskDialog.value = true })
        }
    ) {
        BodyContent(
            tasks,
            onRemoveTask,
            onUpdateTask,
            onOpenEditDialog = {
                taskByDefault.value = it
                isShowEditDialog.value = true
            }
        )
        if (isShowAddTaskDialog.value) {
            ChangeTaskDialog(onAddTask, { isShowAddTaskDialog.value = false })
        }
        if (isShowEditDialog.value) {
            ChangeTaskDialog(
                onUpdateTask,
                {
                    isShowEditDialog.value = false
                    taskByDefault.value = null
                },
                taskByDefault = taskByDefault.value
            )
        }
    }
}


@Composable
fun TodoAppBar(modifier: Modifier = Modifier) {
    TopAppBar(title = { Text("Todo") }, modifier = modifier)
}

@Composable
fun TodoFloatingActionButton(onShowDialog: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { onShowDialog() },
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
    onOpenEditDialog: (Task) -> Unit = {}
) {
    if (tasks.isEmpty()) {
        NoDataFeed("No data", modifier = Modifier)
    } else {
        TaskFeed(tasks, onUpdateTask, onRemoveTask, onOpenEditDialog = onOpenEditDialog)
    }
}

@Composable
fun NoDataFeed(message: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                message,
                fontSize = TextUnit.Companion.Sp(24),
                modifier = Modifier.padding(vertical = 6.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TaskFeed(
    tasks: List<Task>,
    onUpdateTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    modifier: Modifier = Modifier,
    onOpenEditDialog: (Task) -> Unit = {}
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
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    onLongPressed = onOpenEditDialog
                )
            } else {
                TodoItem(
                    task = task,
                    changeCurrentPosition = { currentPosition.value = task.id },
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    onLongPressed = onOpenEditDialog
                )

            }
        }
    }
}


@Preview
@Composable
fun PreviewTodoScreen() {
    TodoScreen(emptyList(), {}, {}, {})
}