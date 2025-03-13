package com.example.todolist.entity

interface DataToDb {
    val taskId: Int
    val taskName: String
    val taskDate: String
    val done: Boolean
}