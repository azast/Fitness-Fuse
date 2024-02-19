package com.example.compsciia.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compsciia.data.local.database.dao.CompletedDao
import com.example.compsciia.data.local.database.entities.CompletedWorkout
import com.example.compsciia.utilities.DATABASE2_NAME

@Database(entities = [CompletedWorkout::class], version = 1, exportSchema = false)
abstract class CompletedDatabase: RoomDatabase() {

    abstract fun getCompletedDao(): CompletedDao

    companion object{
        @Volatile
        private var INSTANCE2: CompletedDatabase? = null

        fun getCompletedDataBase(context: Context): CompletedDatabase {
            return INSTANCE2 ?: synchronized(this){
                val instance2 = Room.databaseBuilder(context.applicationContext, CompletedDatabase::class.java, DATABASE2_NAME).build()
                INSTANCE2 = instance2
                instance2
            }
        }
    }
}