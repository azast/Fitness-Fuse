package com.example.compsciia.data.local.repository

import androidx.lifecycle.LiveData
import com.example.compsciia.data.local.database.entities.CompletedWorkout
import com.example.compsciia.data.local.database.dao.CompletedDao

class CompletedRepository(private val completedDao: CompletedDao) {

    val allCompletedWorkouts: LiveData<List<CompletedWorkout>> = completedDao.getAllCompletedWorkouts()

    suspend fun insert(completedWorkout: CompletedWorkout){
        completedDao.insertCompleted(completedWorkout)
    }
    suspend fun delete(completedWorkout: CompletedWorkout){
        completedDao.deleteCompleted(completedWorkout)
    }
}
