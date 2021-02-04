package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormWithTwoTextFields(
    onSubmit: (String, String) -> Unit,
    onCancel: () -> Unit,
    validatorTitle: (String) -> Boolean,
    validatorDescription: (String) -> Boolean,
    titleTextField1: String = "", titleTextField2: String = ""
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    TextFieldWithTitle(
        onValueChange = { title = it },
        title = titleTextField1,
        modifier = Modifier.padding(8.dp),
        validator = validatorTitle,
    )
    TextFieldWithTitle(
        onValueChange = { description = it },
        title = titleTextField2,
        modifier = Modifier.padding(8.dp),
        validator = validatorDescription
    )
    FormControlButtons(
        onSubmit = {
            if (validatorTitle(title) && validatorDescription(description)) {
                onSubmit(title, description)
            }
        },
        onCancel = onCancel
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