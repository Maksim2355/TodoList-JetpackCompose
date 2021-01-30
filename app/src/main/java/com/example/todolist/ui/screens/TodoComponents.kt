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
import com.example.todolist.data.repository.MockRepository
import java.util.*

@Composable
fun AddTaskDialog(
    onAddTask: (Task) -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val taskName = remember {
        mutableStateOf("")
    }
    val taskDescription = remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onDismissDialog() }) {
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
                            val task = Task(
                                MockRepository.generateId(),
                                taskName.value,
                                taskDescription.value
                            )
                            onAddTask(task)
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(text = "Добавить")
                    }
                    Button(
                        onClick = { onDismissDialog() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(text = "Удалить")
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
