package com.example.compsciia.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compsciia.data.local.database.entities.Workout
import com.example.compsciia.data.local.database.entities.WorkoutRecord


@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: WorkoutRecord)

    @Query("Select * from record_table order by id ASC")
    fun getAllWorkoutsRecord(): LiveData<List<WorkoutRecord>>


    @Query("UPDATE record_table Set record= :record, `desc` = :desc, type = :type, diet= :diet  WHERE id= :id ")
    suspend fun update(id: Int?, record: String?, desc: String?,type:String?,diet:String?)
}