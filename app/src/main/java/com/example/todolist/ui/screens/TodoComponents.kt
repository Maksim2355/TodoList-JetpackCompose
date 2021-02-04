package com.example.todolist.ui.screens

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue


@Composable
fun TextFieldWithTitle(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    initValue: String = "",
    title: String = "",
    maxLines: Int = Int.MAX_VALUE,
    backgroundColor: Color = MaterialTheme.colors.background,
    validator: (String) -> Boolean = { true },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onImeActionPerformed: (ImeAction, SoftwareKeyboardController?) -> Unit = { _, _ -> }
) {
    var isErrorValue by remember { mutableStateOf(false) }
    var valueTextField by remember { mutableStateOf(initValue) }
    if (title.isNotEmpty()) {
        Text(title, style = MaterialTheme.typography.body1)
    }
    TextField(
        value = valueTextField,
        onValueChange = {
            valueTextField = it
            isErrorValue = if (validator(it)) {
                onValueChange(it)
                false
            } else {
                true
            }
        },
        modifier = modifier,
        maxLines = maxLines,
        backgroundColor = backgroundColor,
        isErrorValue = isErrorValue,
        keyboardOptions = keyboardOptions,
        onImeActionPerformed = onImeActionPerformed
    )
}
