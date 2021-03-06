package com.example.todolist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todolist.common.validateDescription
import com.example.todolist.common.validateTitle
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
        TextWithIconButtonRow(
            title = task.name, icon = task.status.imageVector,
            modifier = Modifier.fillMaxWidth()
        )
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
    var isEditingMode by remember { mutableStateOf(false) }
    TodoCard(
        onClick = {
            if (isEditingMode) isEditingMode = false
            else changeCurrentPosition(null)
        },
        onLongClick = { isEditingMode = !isEditingMode },
        modifier = modifier
    ) {
        if (isEditingMode) {
            TodoItemEditor(
                todoTitle = task.name,
                todoDescription = task.description,
                onTitleChanged = { onUpdateTask(task.copy(name = it)) },
                onDescriptionChanged = { onUpdateTask(task.copy(description = it)) },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            TodoDetails(
                title = task.name,
                description = task.description,
                icon = Icons.Default.Delete,
                onIconClick = { onRemoveTask(task) })
        }
        TaskStatusRadioGroup(
            currentStatus = task.status,
            onChangeStatus = { updatedStatus ->
                val updatedTask = task.copy(status = updatedStatus)
                onUpdateTask(updatedTask)
            }
        )
    }
}

@Composable
fun TodoItemEditor(
    todoTitle: String,
    todoDescription: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextFieldWithTitle(
        initValue = todoTitle,
        onValueChange = { onTitleChanged(it) },
        modifier = modifier.padding(6.dp),
        validator = validateTitle
    )
    TextFieldWithTitle(
        initValue = todoDescription,
        onValueChange = { onDescriptionChanged(it) },
        modifier = modifier.padding(6.dp),
        validator = validateDescription,
    )
}

@Composable
fun TodoDetails(
    title: String,
    description: String,
    icon: ImageVector,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextWithIconButtonRow(
        title = title,
        icon = icon,
        onIconClick = { onIconClick() },
        modifier = modifier
    )
    Text(text = description)
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
fun TextWithIconButtonRow(
    title: String, icon: ImageVector,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(6f),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = onIconClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = icon, null)
        }
    }
}

@Composable
fun TaskStatusRadioGroup(
    currentStatus: StatusTask,
    onChangeStatus: (StatusTask) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        for (status in StatusTask.values()) {
            val iconTint = if (currentStatus == status) Color.Blue else Color.Black
            IconButton(
                onClick = { onChangeStatus(status) },
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Icon(imageVector = status.imageVector, null, tint = iconTint)
            }
        }
    }
}

