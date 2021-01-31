package com.example.todolist

import android.app.Application
import com.example.todolist.data.db.TaskDao
import com.example.todolist.data.db.TaskDbOpenHelper

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        dao = TaskDbOpenHelper(instance)
    }

    companion object {
        //Нормальные люди не используют тут lateinit
        private lateinit var instance: App
        lateinit var dao: TaskDao
    }

}