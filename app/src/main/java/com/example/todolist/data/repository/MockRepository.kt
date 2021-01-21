package com.example.todolist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.model.StatusTask
import com.example.todolist.data.model.Task


class MockRepository : TaskRepository {

    private var _listTask: List<Task> = listOf(
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
            "ОБОЖАЮ ДОТУ КАК Я ОБОЖАЮ ДОТУААААААААААААААААААААААААААААААААААААААААААА ",
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

    override val observedTask: LiveData<List<Task>>
        get() = _taskStreams

    override fun addNewTask(task: Task) {
        val tmpList = _listTask.toMutableList()
        tmpList.add(task)
        _listTask = tmpList
        _taskStreams.postValue(tmpList)
    }

    override fun updateTask(task: Task) {
        val tmpList = _listTask.toMutableList()
        val taskNoEdit = tmpList.find { it.id == task.id }
        val indexElement = tmpList.indexOf(taskNoEdit)
        if (indexElement != -1) tmpList[indexElement] = task
        _listTask = tmpList
        _taskStreams.postValue(tmpList)
    }

    override fun removeTask(task: Task) {
        val tmpList = _listTask.toMutableList()
        tmpList.remove(task)
        _listTask = tmpList
        _taskStreams.postValue(tmpList)
    }

    companion object {
        private var taskId = 0
        fun generateId() = taskId++
    }

}