package com.example.todolist.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.FormatColorReset
import androidx.compose.ui.graphics.vector.ImageVector

data class Task(val id: Int,
                val name: String,
                val description: String,
                val status: StatusTask)

enum class StatusTask(val imageVector: ImageVector) {
    InQueue(Icons.Filled.FormatColorReset),
    InProgress(Icons.Filled.FontDownload),
    Completed(Icons.Filled.Equalizer)
}
