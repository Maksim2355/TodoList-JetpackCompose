package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.todolist.data.model.Task


@Composable
fun TodoScreen(
    tasks: List<Task>,
    onAddTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit,
    filterTaskByQuery: (String) -> Unit
) {
    val isShowAddTaskDialog = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            SearchAppBar(onQueryChange = filterTaskByQuery)
        },
        floatingActionButton = {
            TodoFloatingActionButton(onShowDialog = { isShowAddTaskDialog.value = true })
        }
    ) {
        BodyContent(
            tasks,
            onRemoveTask,
            onUpdateTask,
        )
        if (isShowAddTaskDialog.value) {
            ChangeTaskDialog(
                onChangeTask = onAddTask,
                onDismissDialog = { isShowAddTaskDialog.value = false }
            )
        }
    }
}


@Composable
fun SearchAppBar(onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    val query = remember { mutableStateOf("") }
    Column(
        modifier = modifier,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.primary,
            elevation = 8.dp
        ) {
            val onValueChange: (String) -> Unit = {
                query.value = it
                onQueryChange(it)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = query.value,
                    onValueChange = onValueChange,
                    label = {
                        Text("Search")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, null)
                    },
                    trailingIcon = {
                        IconButton(onClick = { onValueChange("") }) {
                            Icon(Icons.Default.Clear, null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    onImeActionPerformed = { imeAction, softwareKeyboardController ->
                        if (imeAction == ImeAction.Done) {
                            softwareKeyboardController?.hideSoftwareKeyboard()
                        }
                    },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    backgroundColor = MaterialTheme.colors.surface,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
                )
            }
        }
    }
}

@Composable
fun TodoFloatingActionButton(onShowDialog: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { onShowDialog() },
        backgroundColor = Color.Blue,
        contentColor = Color.White,
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Filled.Add, null)
    }
}

@Composable
fun BodyContent(
    tasks: List<Task>,
    onRemoveTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit,
) {
    if (tasks.isEmpty()) {
        NoDataMessage(
            "No data",
            modifier = Modifier
        )
    } else {
        TaskFeed(tasks, onUpdateTask, onRemoveTask)
    }
}

@Composable
fun NoDataMessage(message: String, modifier: Modifier = Modifier) {
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
) {
    val currentPosition: MutableState<Int?> = remember { mutableStateOf(null) }
    LazyColumn(
        modifier = modifier
    ) {
        items(items = tasks) { task ->
            if (task.id == currentPosition.value) {
                TodoItemDetails(
                    task = task,
                    changeCurrentPosition = { currentPosition.value = null },
                    onUpdateTask = onUpdateTask,
                    onRemoveTask = onRemoveTask,
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                )
            } else {
                TodoItem(
                    task = task,
                    changeCurrentPosition = { currentPosition.value = task.id },
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewTodoScreen() {
    TodoScreen(emptyList(), {}, {}, {}, {})
}