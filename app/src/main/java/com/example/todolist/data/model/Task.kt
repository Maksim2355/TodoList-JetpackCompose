package com.example.todolist.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class Task(val id: Int,
                val name: String,
                val description: String,
                val status: StatusTask = StatusTask.InQueue)

enum class StatusTask(val imageVector: ImageVector) {
    InQueue(Icons.Filled.PendingActions),
    InProgress(Icons.Filled.Restore),
    Completed(Icons.Filled.Done)
}
