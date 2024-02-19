package com.example.compsciia.data.local.repository

import androidx.lifecycle.LiveData
import com.example.compsciia.data.local.database.RecordDatabase
import com.example.compsciia.data.local.database.dao.RecordDao
import com.example.compsciia.data.local.database.entities.Workout
import com.example.compsciia.data.local.database.dao.WorkoutDao
import com.example.compsciia.data.local.database.entities.WorkoutRecord

class WorkoutsRecordRepository(private val recordDao: RecordDao) {

    val allWorkouts: LiveData<List<WorkoutRecord>> = recordDao.getAllWorkoutsRecord()

    suspend fun insert(record: WorkoutRecord){
        recordDao.insert(record)
    }

    suspend fun update(record: WorkoutRecord){
        recordDao.update(record.id, record.title, record.desc, type = record.type, diet = record.diet)
    }
}