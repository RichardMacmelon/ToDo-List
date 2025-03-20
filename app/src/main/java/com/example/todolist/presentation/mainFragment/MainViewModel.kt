package com.example.todolist.presentation.mainFragment

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TaskDb
import com.example.todolist.domain.DeleteAllTaskByDoneUseCase
import com.example.todolist.domain.GetTasksByDoneStatusUseCase
import com.example.todolist.domain.MarkTaskAsDoneUseCase
import com.example.todolist.domain.MarkTaskAsNotDoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksByDoneStatusUseCase: GetTasksByDoneStatusUseCase,
    private val deleteAllTaskByDoneUseCase: DeleteAllTaskByDoneUseCase,
    private val markTaskAsDoneUseCase: MarkTaskAsDoneUseCase,
    private val markTaskAsNotDoneUseCase: MarkTaskAsNotDoneUseCase
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

    fun deleteAllTaskByDone(done: Boolean) {
        viewModelScope.launch { deleteAllTaskByDoneUseCase.deleteAllTaskByDone(done) }
    }

    fun markTaskAsDone(taskId: Int) {
        viewModelScope.launch {
            markTaskAsDoneUseCase.markTaskAsDone(taskId)
        }
    }

    fun markTaskAsNotDone(taskId: Int) {
        viewModelScope.launch {
            markTaskAsNotDoneUseCase.markTaskAsNotDone(taskId)
        }
    }

}