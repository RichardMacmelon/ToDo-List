package com.example.todolist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TaskDb
import com.example.todolist.domain.GetAllInfoFromDbUseCase
import com.example.todolist.domain.InsertToDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllInfoFromDbUseCase: GetAllInfoFromDbUseCase,
    private val insertToDbUseCase: InsertToDbUseCase
) : ViewModel() {

    val allTask = this.getAllInfoFromDbUseCase.getAllInfo().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun add() {
        val task = TaskDb(
            taskName = "Kek",
            taskDate = "12:00",
            done = false
        )
        viewModelScope.launch {
            insertToDbUseCase.insert(task)
        }
    }

}