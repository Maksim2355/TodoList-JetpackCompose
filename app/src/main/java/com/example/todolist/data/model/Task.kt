package com.example.todolist.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class Task(val id: Int,
                var name: String,
                var description: String,
                var status: StatusTask)

enum class StatusTask(val imageVector: ImageVector) {
    InQueue(Icons.Filled.PendingActions),
    InProgress(Icons.Filled.Restore),
    Completed(Icons.Filled.Done)
}
