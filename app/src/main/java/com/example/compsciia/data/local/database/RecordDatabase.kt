package com.example.compsciia.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compsciia.data.local.database.dao.RecordDao
import com.example.compsciia.data.local.database.entities.WorkoutRecord
import com.example.compsciia.utilities.DATABASE3_NAME


@Database(entities = [WorkoutRecord::class], version = 1, exportSchema = false)
abstract class RecordDatabase : RoomDatabase() {

    abstract fun getRecordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: RecordDatabase? = null

        fun getDataBase(context: Context): RecordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecordDatabase::class.java,
                    DATABASE3_NAME
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }

}