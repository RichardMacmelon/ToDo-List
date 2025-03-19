package com.example.todolist.domain

import com.example.todolist.data.MainRepository
import com.example.todolist.data.TaskDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksByDoneStatusUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun getNeedTasks(isDone: Boolean) : Flow<List<TaskDb>> {
        return mainRepository.getDBInfo().getTasksByDoneStatus(isDone)
    }
}