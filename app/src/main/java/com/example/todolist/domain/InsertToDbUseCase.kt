package com.example.todolist.domain

import com.example.todolist.data.MainRepository
import com.example.todolist.data.TaskDb
import javax.inject.Inject

class InsertToDbUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun insert(taskDb: TaskDb) {
        return mainRepository.getDBInfo().insertTask(taskDb)
    }
}