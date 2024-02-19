package com.example.compsciia.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.compsciia.data.local.database.entities.CompletedWorkout
import com.example.compsciia.data.local.database.CompletedDatabase
import com.example.compsciia.data.local.repository.CompletedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompletedViewModel(application: Application) : AndroidViewModel(application) {

    private val completedRepository: CompletedRepository
    val allCompletedWorkouts: LiveData<List<CompletedWorkout>>

    init {
        val dao2 = CompletedDatabase.getCompletedDataBase(application).getCompletedDao()
        completedRepository = CompletedRepository(dao2)
        allCompletedWorkouts = completedRepository.allCompletedWorkouts
    }

    fun deleteCompleted(completedWorkout: CompletedWorkout) = viewModelScope.launch(Dispatchers.IO) {
        completedRepository.delete(completedWorkout)
    }

    fun insertCompleted(completedWorkout: CompletedWorkout) = viewModelScope.launch(Dispatchers.IO) {
        completedRepository.insert(completedWorkout)
    }
}
