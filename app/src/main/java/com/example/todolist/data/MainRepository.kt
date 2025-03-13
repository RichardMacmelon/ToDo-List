package com.example.todolist.data

import javax.inject.Inject

class MainRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getDBInfo(): TaskDao {
        return taskDao
    }
}