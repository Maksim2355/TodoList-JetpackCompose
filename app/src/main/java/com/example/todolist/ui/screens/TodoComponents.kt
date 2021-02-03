package com.example.todolist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
                    description,
                    System.currentTimeMillis()
                )
                onChangeTask(task)
            }, onCancel = { onDismissDialog() },
            titleTextField1 = "Название задачи",
            titleTextField2 = "Описание задачи"
        )
    }
}


@Composable
fun TextFieldWithTitle(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    initValue: String = "",
    title: String = "",
    maxLines: Int = Int.MAX_VALUE,
    backgroundColor: Color = MaterialTheme.colors.background,
    validator: (String) -> Boolean = { true }
) {
    val isErrorValue = remember { mutableStateOf(false) }
    val valueTextField = remember { mutableStateOf(initValue) }
    if (title.isNotEmpty()) {
        Text(title, style = MaterialTheme.typography.body1)
    }
    TextField(
        value = valueTextField.value,
        onValueChange = {
            valueTextField.value = it
            if (validator(it)) {
                onValueChange(it)
                isErrorValue.value = false
            } else {
                isErrorValue.value = true
            }
        },
        modifier = modifier,
        maxLines = maxLines,
        backgroundColor = backgroundColor,
        isErrorValue = isErrorValue.value
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
fun FormWithTwoTextFields(
    onSubmit: (String, String) -> Unit, onCancel: () -> Unit,
    titleTextField1: String = "", titleTextField2: String = ""
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val validator: (String) -> Boolean = { it.length > 4 }
    TextFieldWithTitle(
        onValueChange = { title.value = it },
        title = titleTextField1,
        modifier = Modifier.padding(8.dp),
        validator = validator
    )
    TextFieldWithTitle(
        onValueChange = { description.value = it },
        title = titleTextField2,
        modifier = Modifier.padding(8.dp),
        validator = validator
    )
    FormControlButtons(
        onSubmit = {
            if (validator(title.value) && validator(description.value)) {
                onSubmit(title.value, description.value)
            }
        },
        onCancel = onCancel
    )
}

@Composable
fun PopUpMenu(
    title: String,
    menuItems: List<String>,
    onClickMenuItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isShowMenu by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        DropdownMenu(
            toggle = { IconPopUpMenu(onClick = { isShowMenu = true }) },
            expanded = isShowMenu,
            onDismissRequest = { isShowMenu = false },
        ) {
            ItemPopUpMenu(
                item = title,
                isClickable = false,
            )
            menuItems.forEach {
                ItemPopUpMenu(item = it, onClick = {
                    onClickMenuItem(it)
                    isShowMenu = false
                })
            }
        }
    }

}

@Composable
fun ItemPopUpMenu(
    item: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isClickable: Boolean = true,
) {
    Text(
        text = item,
        modifier = modifier.fillMaxWidth().clickable(isClickable) { onClick() }
            .padding(16.dp)

    )
}

@Composable
fun IconPopUpMenu(
    icon: ImageVector = Icons.Default.MoreVert,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = "Menu")
    }
}