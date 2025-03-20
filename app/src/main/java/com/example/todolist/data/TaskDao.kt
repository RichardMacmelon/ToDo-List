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

    @Query("DELETE FROM taskDb WHERE done = :done")
    suspend fun deleteAllTaskByDone(done: Boolean)

    @Query("UPDATE taskDb SET done = 1 WHERE task_id = :taskId")
    suspend fun markTaskAsDone(taskId: Int)

    @Query("UPDATE taskDb SET done = 0  WHERE task_id = :taskId")
    suspend fun markTaskAsNotDone(taskId: Int)
}