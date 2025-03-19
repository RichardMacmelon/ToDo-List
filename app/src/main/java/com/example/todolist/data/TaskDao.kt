package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskDb WHERE done = :isDone")
    fun getTasksByDoneStatus(isDone: Boolean): Flow<List<TaskDb>>

    @Insert
    suspend fun insertTask(film: TaskDb)
}