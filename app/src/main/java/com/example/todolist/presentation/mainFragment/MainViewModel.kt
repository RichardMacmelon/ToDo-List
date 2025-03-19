package com.example.todolist.presentation.mainFragment

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TaskDb
import com.example.todolist.domain.GetTasksByDoneStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksByDoneStatusUseCase: GetTasksByDoneStatusUseCase,
) : ViewModel() {

    val allPlannedTask = this.getTasksByDoneStatusUseCase.getNeedTasks(false).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val allCompletedTask = this.getTasksByDoneStatusUseCase.getNeedTasks(true).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val tasks = MutableLiveData<List<TaskDb>>(listOf())
    val isNoTasks = MediatorLiveData<Boolean>().apply {
        addSource(tasks) { value = it.isEmpty() }
    }

}