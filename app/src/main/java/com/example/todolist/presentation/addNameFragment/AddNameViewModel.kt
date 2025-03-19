package com.example.todolist.presentation.addNameFragment

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TaskDb
import com.example.todolist.domain.InsertToDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNameViewModel @Inject constructor(
    private val insertToDbUseCase: InsertToDbUseCase
) : ViewModel() {

    val nameText = MutableLiveData("")
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(nameText) { value = it.isNotBlank() }
    }

    fun addToCollection(task: TaskDb) {
        viewModelScope.launch {
            insertToDbUseCase.insert(task)
        }
    }
}