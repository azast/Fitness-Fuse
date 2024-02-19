package com.example.compsciia.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.compsciia.data.local.database.entities.Workout
import com.example.compsciia.data.local.database.WorkoutDatabase
import com.example.compsciia.data.local.repository.WorkoutsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application): AndroidViewModel(application) {

    private val repository : WorkoutsRepository
    val allWorkouts : LiveData<List<Workout>>

    init {
        val dao = WorkoutDatabase.getDataBase(application).getWorkoutDao()
        repository = WorkoutsRepository(dao)
        allWorkouts = repository.allWorkouts
    }

    fun deleteWorkout(workout: Workout) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(workout)
    }
    fun insertWorkout(workout: Workout)= viewModelScope.launch(Dispatchers.IO) {
        repository.insert(workout)
    }
    fun updateWorkout(workout: Workout)= viewModelScope.launch(Dispatchers.IO) {
        repository.update(workout)
    }
}