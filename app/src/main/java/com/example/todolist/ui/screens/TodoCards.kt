package com.example.todolist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.longPressGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todolist.data.model.StatusTask
import com.example.todolist.data.model.Task


@Composable
fun TodoItem(
    task: Task,
    changeCurrentPosition: (Int?) -> Unit,
    modifier: Modifier = Modifier,
    onLongPressed: (Task) -> Unit = {}
) {
    Card(
        modifier = modifier.clickable(onClick = { changeCurrentPosition(task.id) })
            .longPressGestureFilter {
                onLongPressed(task)
            },
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
            Icon(
                imageVector = task.status.imageVector,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TodoItemEditing(
    task: Task,
    changeCurrentPosition: (Int?) -> Unit,
    onUpdateTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    modifier: Modifier = Modifier,
    onLongPressed: (Task) -> Unit = {}
) {
    Card(
        modifier = modifier.clickable(onClick = { changeCurrentPosition(null) })
            .longPressGestureFilter { onLongPressed(task) },
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.name,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(6f)
                )
                IconButton(
                    onClick = { onRemoveTask(task) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Filled.Delete)
                }
            }
            TaskStatusRadioGroup(
                currentStatus = task.status,
                onChangeStatus = { updatedStatus ->
                    val updatedTask = task.copy(status = updatedStatus)
                    onUpdateTask(updatedTask)
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
        modifier = modifier
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

