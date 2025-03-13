package com.example.todolist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.entity.DataToDb

@Entity(tableName = "taskDb")
data class TaskDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("task_id")
    override val taskId: Int = 0,
    @ColumnInfo("task_name")
    override val taskName: String,
    @ColumnInfo("task_date")
    override val taskDate: String,
    @ColumnInfo("done")
    override val done: Boolean
) : DataToDb
