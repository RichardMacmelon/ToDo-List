package com.example.todolist.domain

import com.example.todolist.data.MainRepository
import javax.inject.Inject

class MarkTaskAsDoneUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun markTaskAsDone(taskId : Int) {
        mainRepository.getDBInfo().markTaskAsDone(taskId)
    }
}