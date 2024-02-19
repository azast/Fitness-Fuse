package com.example.compsciia.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "workouts_table")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "workout") val workout: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "diet") val diet: String?
) : java.io.Serializable

