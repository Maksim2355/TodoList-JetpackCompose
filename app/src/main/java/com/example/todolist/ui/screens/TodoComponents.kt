package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


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
    var valueTextField by remember { mutableStateOf(TextFieldValue(initValue)) }


    if (title.isNotEmpty()) {
        Text(title, style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold)
    }
    TextField(
        value = valueTextField,
        onValueChange = {
            valueTextField = it
            isErrorValue = if (validator(it.text)) {
                onValueChange(it.text)
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


@Composable
fun SearchAppBar(
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf(TextFieldValue()) }
    val onValueChange: (TextFieldValue) -> Unit = {
        query = it
        onQueryChange(it.text)
    }
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            TextField(
                value = query,
                onValueChange = onValueChange,
                label = {
                    Text("Search")
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, null)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        onValueChange(TextFieldValue())
                    }) {
                        Icon(Icons.Default.Clear, null)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                onImeActionPerformed = { imeAction, softwareKeyboardController ->
                    if (imeAction == ImeAction.Done) {
                        softwareKeyboardController?.hideSoftwareKeyboard()
                    }
                },
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                backgroundColor = MaterialTheme.colors.surface,
                singleLine = true,
                modifier = Modifier.weight(8f)

            )
        }
    }
}