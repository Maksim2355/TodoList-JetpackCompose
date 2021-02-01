package com.example.todolist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todolist.data.model.StatusTask
import com.example.todolist.data.model.Task


@Composable
fun TodoItem(
    task: Task,
    changeCurrentPosition: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    TodoCard(
        onClick = { changeCurrentPosition(task.id) },
        modifier = modifier
    ) {
        TodoRow(title = task.name, icon = task.status.imageVector)
    }
}

@Composable
fun TodoItemDetails(
    task: Task,
    changeCurrentPosition: (Int?) -> Unit,
    onUpdateTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEditingMode = remember {
        mutableStateOf(false)
    }
    val title = remember { mutableStateOf(task.name) }
    val description = remember { mutableStateOf(task.description) }
    TodoCard(
        onClick = {
            if (isEditingMode.value) isEditingMode.value = false
            else changeCurrentPosition(null)
        },
        onLongClick = { isEditingMode.value = !isEditingMode.value },
        modifier = modifier
    ) {
        if (isEditingMode.value) {
            TextFieldWithTitle(
                value = title.value,
                onValueChange = {
                    title.value = it
                    val editTask = task.copy(name = it)
                    onUpdateTask(editTask)
                },
                modifier = Modifier.padding(6.dp)
            )
            TextFieldWithTitle(
                value = description.value,
                onValueChange = {
                    description.value = it
                    val editTask = task.copy(description = it)
                    onUpdateTask(editTask)
                },
                modifier = Modifier.padding(6.dp)
            )
        } else {
            TodoRow(
                title = task.name,
                icon = Icons.Default.Delete,
                onIconClick = { onRemoveTask(task) }
            )
            Text(text = task.description)
        }
        TaskStatusRadioGroup(
            currentStatus = task.status,
            onChangeStatus = { updatedStatus ->
                val updatedTask = task.copy(status = updatedStatus)
                onUpdateTask(updatedTask)
            },
        )
    }
}

@Composable
fun TodoItemEditor() {
    TodoCard() {

    }
}

@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.clickable(
            onClick = { onClick() },
            onLongClick = { onLongClick() }
        ),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            content()
        }
    }
}

@Composable
fun TodoRow(
    title: String, icon: ImageVector, modifier: Modifier = Modifier,
    onIconClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(6f)
        )
        IconButton(
            onClick = onIconClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = icon)
        }
    }
}

@Composable
fun TaskStatusRadioGroup(
    currentStatus: StatusTask,
    onChangeStatus: (StatusTask) -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center
) {
    Row(
        modifier = modifier
    ) {
        for (status in StatusTask.values()) {
            val iconTint = if (currentStatus == status) Color.Blue else Color.Black
            IconButton(
                onClick = { onChangeStatus(status) },
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Icon(imageVector = status.imageVector, tint = iconTint)
            }
        }
    }
}

