package com.example.compsciia.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compsciia.data.local.database.entities.CompletedWorkout

@Dao
interface CompletedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompleted(completed: CompletedWorkout)

    @Delete
    suspend fun deleteCompleted(completed: CompletedWorkout)

    @Query("SELECT * FROM completed_table ORDER BY id2 DESC")
    fun getAllCompletedWorkouts(): LiveData<List<CompletedWorkout>>

}