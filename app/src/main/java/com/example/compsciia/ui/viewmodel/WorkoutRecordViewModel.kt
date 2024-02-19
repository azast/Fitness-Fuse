package com.example.compsciia.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.compsciia.data.local.database.RecordDatabase
import com.example.compsciia.data.local.database.entities.Workout
import com.example.compsciia.data.local.database.WorkoutDatabase
import com.example.compsciia.data.local.database.dao.RecordDao
import com.example.compsciia.data.local.database.entities.WorkoutRecord
import com.example.compsciia.data.local.repository.WorkoutsRecordRepository
import com.example.compsciia.data.local.repository.WorkoutsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutRecordViewModel(application: Application): AndroidViewModel(application) {

    private val repository : WorkoutsRecordRepository
    val allWorkoutsRecord : LiveData<List<WorkoutRecord>>

    init {
        val dao = RecordDatabase.getDataBase(application).getRecordDao()
        repository = WorkoutsRecordRepository(dao)
        allWorkoutsRecord = repository.allWorkouts
    }

    fun insertWorkout(record: WorkoutRecord)= viewModelScope.launch(Dispatchers.IO) {
        repository.insert(record)
    }
    fun updateWorkout(record: WorkoutRecord)= viewModelScope.launch(Dispatchers.IO) {
        repository.update(record)
    }
}