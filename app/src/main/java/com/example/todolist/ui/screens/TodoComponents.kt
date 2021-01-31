package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.todolist.data.model.Task

@Composable
fun ChangeTaskDialog(
    onChangeTask: (Task) -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
    taskByDefault: Task? = null
) {
    val taskName = remember {
        mutableStateOf(taskByDefault?.name ?: "")
    }
    val taskDescription = remember {
        mutableStateOf(taskByDefault?.description ?: "")
    }
    Dialog(onDismissRequest = { }) {
        Card() {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(16.dp)
            ) {
                TaskTextField(
                    value = taskName.value,
                    onValueChange = { taskName.value = it },
                    title = "Название задачи",
                    modifier = Modifier.padding(8.dp)
                )
                TaskTextField(
                    value = taskDescription.value,
                    onValueChange = { taskDescription.value = it },
                    title = "Описание задачи",
                    modifier = Modifier.padding(8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismissDialog()
                            val task = taskByDefault?.copy(
                                name = taskName.value,
                                description = taskDescription.value
                            ) ?: Task(
                                0,
                                taskName.value,
                                taskDescription.value
                            )
                            onChangeTask(task)
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(text = "Отправить")
                    }
                    Button(
                        onClick = { onDismissDialog() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(text = "Отменить")
                    }
                }
            }
        }
    }
}

@Composable
fun TaskTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
    maxLines: Int = Int.MAX_VALUE
) {
    if (title.isNotEmpty()) {
        Text(
            title,
            style = MaterialTheme.typography.body1
        )
    }
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        maxLines = maxLines
    )
}