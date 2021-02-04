package com.example.todolist.common

val validateTitle: (String) -> Boolean = {it.length > 4}
val validateDescription: (String) -> Boolean = {it.length > 8}