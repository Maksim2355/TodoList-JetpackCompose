package com.example.todolist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.model.StatusTask
import com.example.todolist.data.model.Task


class MockRepository : TaskRepository {

    private val _listTask: MutableList<Task> = mutableListOf(
        Task(
            generateId(),
            "Приготовить питсу",
            "Я хочу жрать, значит хачу питсу люблю питсу оч люблю",
            StatusTask.InQueue
        ),
        Task(
            generateId(),
            "ААААА Я ЗАВАЛИЛ ТЕРВЕР",
            "БОЖЕ НЕТ БОЖЕТ НЕТ МАТОЖИДАНИЕ ДИСПЕРСИЯ ХОТЯ ВАООБЩЕ ПОФИГ",
            StatusTask.InQueue
        ),
        Task(
            generateId(),
            "ЩАС БЫ В ДОТКУ",
            "ОБОЖАЮ ДОТУ КАК Я ОБОЖАЮ ДОТУААААААААААААААААААААААААААААААААААААААААААА",
            StatusTask.InQueue
        ),
        Task(
            generateId(),
            "Купить цветы девушки",
            "Лол, вы там совсем конч? У меня ее нет. Дурные совсем стали",
            StatusTask.InQueue
        ),
        Task(
            generateId(),
            "ПХлеб баотгн свечи для геморрой",
            "купить лекарстава и свечи, но не в аптеке но в аптечке люблю котлин",
            StatusTask.InQueue
        ),
        Task(
            generateId(),
            "Сделать приложение для андроид и для иос",
            "я хочу написать что-нибудь с компоусом",
            StatusTask.InQueue
        )
    )

    private val _taskStreams: MutableLiveData<List<Task>> = MutableLiveData(_listTask)

    override fun observeTask(): LiveData<List<Task>> = _taskStreams

    override fun updateTask(task: Task) {
        val taskNoEdit = _listTask.find { it.id == taskId }
        val indexElement = _listTask.indexOf(taskNoEdit)
        if (indexElement != -1) _listTask[indexElement] = task
        _taskStreams.postValue(_listTask)
    }

    override fun addNewTask(task: Task) {
        _listTask.add(task)
        _taskStreams.postValue(_listTask)
    }

    override fun removeTask(task: Task) {
        _listTask.remove(task)
        _taskStreams.postValue(_listTask)
    }

    companion object {
        private var taskId = 0
        fun generateId() = taskId++
    }

}