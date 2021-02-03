package com.example.todolist.common

interface FilterInteractor {

    fun filterByQuery(query: String)

    fun filterByDate(params: FilterParams)

}

enum class FilterParams(title: String) {
    Descending("Новые задачи"), Ascending("Старые задачи")
}