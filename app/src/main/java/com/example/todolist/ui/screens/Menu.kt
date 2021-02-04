package com.example.todolist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

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