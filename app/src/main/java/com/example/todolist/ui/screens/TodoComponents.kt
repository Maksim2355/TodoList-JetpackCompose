package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
                    description
                )
                onChangeTask(task)
            }, onCancel = { onDismissDialog() }
        )
    }

}


@Composable
fun TextFieldWithTitle(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
    maxLines: Int = Int.MAX_VALUE,
    backgroundColor: Color = MaterialTheme.colors.background,
) {
    if (title.isNotEmpty()) {
        Text(title, style = MaterialTheme.typography.body1)
    }
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        maxLines = maxLines,
        backgroundColor = backgroundColor
    )
}


@Composable
fun FormControlButtons(onSubmit: () -> Unit, onCancel: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onSubmit,
            modifier = Modifier.padding(8.dp),
        ) {
            Text(text = "Отправить")
        }
        Button(
            onClick = onCancel,
            modifier = Modifier.padding(8.dp),
        ) {
            Text(text = "Отменить")
        }
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

@Composable
fun FormWithTwoTextFields(onSubmit: (String, String) -> Unit, onCancel: () -> Unit) {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    TextFieldWithTitle(
        value = title.value,
        onValueChange = { title.value = it },
        title = "Название задачи",
        modifier = Modifier.padding(8.dp)
    )
    TextFieldWithTitle(
        value = description.value,
        onValueChange = { description.value = it },
        title = "Описание задачи",
        modifier = Modifier.padding(8.dp)
    )
    FormControlButtons(
        onSubmit = { onSubmit(title.value, description.value) },
        onCancel = onCancel
    )

}