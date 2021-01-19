package com.example.todolist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.model.StatusTask
import com.example.todolist.data.model.Task
import com.example.todolist.data.repository.MockRepository


@Composable
fun TodoScreen(
    tasks: LiveData<List<Task>>,
    currentEditPosition: LiveData<Int?>,
    changeCurrentPosition: (Int?) -> Unit,
    onAddTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit
) {
    val editPosition by currentEditPosition.observeAsState(initial = null)
    Scaffold(
        topBar = {
            TodoAppBar()
        },
        floatingActionButton = {
            TodoFloatingActionButton()
        }
    ) {
        BodyContent(
            tasks = tasks,
            editPosition,
            changeCurrentPosition,
            onRemoveTask,
            onUpdateTask,
            modifier = Modifier.padding(it)
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
        contentColor = Color.White
    ) {
        Icon(imageVector = Icons.Filled.Add)
    }
}

@Composable
fun BodyContent(
    tasks: LiveData<List<Task>>,
    editPosition: Int?,
    changeCurrentPosition: (Int?) -> Unit,
    onRemoveTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val listTask by tasks.observeAsState(initial = emptyList())
    LazyColumn(
        modifier = modifier
    ) {
        items(listTask) {
            if (it.id == editPosition) {
                TodoItemEditing(
                    task = it,
                    changeCurrentPosition = changeCurrentPosition,
                    onUpdateTask = onUpdateTask,
                    onRemoveTask = onRemoveTask,
                    modifier = Modifier.padding(8.dp).fillMaxWidth()
                )

            } else {
                TodoItem(
                    task = it,
                    changeCurrentPosition = changeCurrentPosition,
                    modifier = Modifier.padding(8.dp).fillParentMaxWidth()
                )

            }
        }
    }

}

@Composable
fun TodoItem(
    task: Task,
    changeCurrentPosition: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = { changeCurrentPosition(task.id) }),
        elevation = 2.dp
    ) {
        Row(
            modifier = modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = task.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(6f)
            )
            Icon(imageVector = task.status.imageVector, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun TodoItemEditing(
    task: Task,
    changeCurrentPosition: (Int?) -> Unit,
    onUpdateTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = { changeCurrentPosition(null) }),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = task.name, fontWeight = FontWeight.Bold, modifier = Modifier.weight(4f))
                IconButton(onClick = { onRemoveTask(task) }, modifier = Modifier.weight(1f)) {
                    Icon(imageVector = Icons.Filled.Delete)
                }
            }
            TaskStatusRadioGroup(
                currentStatus = task.status,
                onChangeStatus = { updatedStatus ->
                    task.status = updatedStatus
                    onUpdateTask(task)
                })
            Text(text = task.description)
        }

    }
}

@Composable
fun TaskStatusRadioGroup(
    currentStatus: StatusTask,
    onChangeStatus: (StatusTask) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
    ) {
        for (status in StatusTask.values()) {
            val backgroundTask = if (currentStatus == status) Color.LightGray else Color.White
            IconButton(
                onClick = { onChangeStatus(status) },
                modifier = Modifier.padding(2.dp)
                    .background(backgroundTask, shape = MaterialTheme.shapes.small)
            ) {
                Icon(imageVector = status.imageVector)
            }
        }
    }
}

@Preview
@Composable
fun PreviewTodoScreen() {
    TodoScreen(
        MutableLiveData(
            mutableListOf(
                Task(
                    MockRepository.generateId(),
                    "Приготовить питсу",
                    "Я хочу жрать, значит хачу питсу люблю питсу оч люблю",
                    StatusTask.InQueue
                ),
                Task(
                    MockRepository.generateId(),
                    "ААААА Я ЗАВАЛИЛ ТЕРВЕР",
                    "БОЖЕ НЕТ БОЖЕТ НЕТ МАТОЖИДАНИЕ ДИСПЕРСИЯ ХОТЯ ВАООБЩЕ ПОФИГ",
                    StatusTask.InQueue
                ),
                Task(
                    MockRepository.generateId(),
                    "ЩАС БЫ В ДОТКУ",
                    "ОБОЖАЮ ДОТУ КАК Я ОБОЖАЮ ДОТУААААААААААААААААААААААААААААААААААААААААААА",
                    StatusTask.InQueue
                ),
                Task(
                    MockRepository.generateId(),
                    "Купить цветы девушки",
                    "Лол, вы там совсем конч? У меня ее нет. Дурные совсем стали",
                    StatusTask.InQueue
                ),
                Task(
                    MockRepository.generateId(),
                    "ПХлеб баотгн свечи для геморрой",
                    "купить лекарстава и свечи, но не в аптеке но в аптечке люблю котлин",
                    StatusTask.InQueue
                ),
                Task(
                    MockRepository.generateId(),
                    "Сделать приложение для андроид и для иос",
                    "я хочу написать что-нибудь с компоусом",
                    StatusTask.InQueue
                )
            )
        ), MutableLiveData(1), {}, {}, {}, {})
}