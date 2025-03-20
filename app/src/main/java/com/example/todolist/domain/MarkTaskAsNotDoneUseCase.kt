package com.example.todolist.domain

import com.example.todolist.data.MainRepository
import javax.inject.Inject

class MarkTaskAsNotDoneUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun markTaskAsNotDone(taskId: Int) {
        mainRepository.getDBInfo().markTaskAsNotDone(taskId)
    }

}