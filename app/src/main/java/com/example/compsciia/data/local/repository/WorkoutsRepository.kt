package com.example.compsciia.data.local.repository

import androidx.lifecycle.LiveData
import com.example.compsciia.data.local.database.entities.Workout
import com.example.compsciia.data.local.database.dao.WorkoutDao

class WorkoutsRepository(private val workoutDao: WorkoutDao) {

    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()

    suspend fun insert(workout: Workout){
        workoutDao.insert(workout)
    }
    suspend fun delete(workout: Workout){
        workoutDao.delete(workout)
    }
    suspend fun update(workout: Workout){
        workoutDao.update(workout.id, workout.title, workout.workout, type = workout.type, diet = workout.diet)
    }
}