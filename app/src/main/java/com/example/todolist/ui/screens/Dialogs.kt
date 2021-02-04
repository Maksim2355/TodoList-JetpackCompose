package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.todolist.common.validateDescription
import com.example.todolist.common.validateTitle
import com.example.todolist.data.model.Task

@Composable
fun ChangeTaskDialog(
    onChangeTask: (Task) -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CardDialog(modifier = modifier) {
        FormWithTwoTextFields(
            onSubmit = { title, description ->
                onDismissDialog()
                val task = Task(
                    0,
                    title,
                    description,
                    System.currentTimeMillis()
                )
                onChangeTask(task)
            }, onCancel = { onDismissDialog() },
            titleTextField1 = "Название задачи",
            titleTextField2 = "Описание задачи",
            validatorTitle = validateTitle,
            validatorDescription = validateDescription
        )
    }
}

@Composable
fun CardDialog(
    modifier: Modifier = Modifier,
    onDismissDialog: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissDialog) {
        Card(modifier = modifier) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(16.dp)
            ) { content() }
        }
    }
}