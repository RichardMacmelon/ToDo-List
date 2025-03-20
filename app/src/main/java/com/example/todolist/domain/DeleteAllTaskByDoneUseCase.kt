package com.example.todolist.domain

import com.example.todolist.data.MainRepository
import javax.inject.Inject

class DeleteAllTaskByDoneUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun deleteAllTaskByDone(done: Boolean) {
        mainRepository.getDBInfo().deleteAllTaskByDone(done)
    }
}