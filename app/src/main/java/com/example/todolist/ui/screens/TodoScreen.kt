package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.todolist.common.FilterParams
import com.example.todolist.data.model.Task
import com.example.todolist.ui.purple200
import com.example.todolist.ui.purple500


@Composable
fun TodoScreen(
    tasks: List<Task>,
    onAddTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit,
    filterTaskByQuery: (String) -> Unit,
    sortedTaskByParams: (FilterParams) -> Unit
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
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                cutoutShape = RoundedCornerShape(50),
                backgroundColor = purple500,
            ) {
                val itemsMenu: List<String> = listOf("Новые", "Старые")
                PopUpMenu(
                    "Сортировать по дате",
                    //Если вы такое пишите, то, вероятнее всего, вы - черт
                    menuItems = itemsMenu,
                    onClickMenuItem = {
                        when (it) {
                            itemsMenu[0] -> sortedTaskByParams(FilterParams.Descending)
                            itemsMenu[1] -> sortedTaskByParams(FilterParams.Ascending)
                        }
                    },
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                )
            }
        }
    ) {
        BodyContent(
            tasks,
            onRemoveTask,
            onUpdateTask,
            modifier = Modifier.padding(it)
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
    modifier: Modifier = Modifier
) {
    if (tasks.isEmpty()) {
        NoDataMessage(
            "No data",
            modifier = modifier
        )
    } else {
        TaskFeed(tasks, onUpdateTask, onRemoveTask, modifier = modifier)
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
