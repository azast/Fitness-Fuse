package com.example.compsciia.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "record_table")
data class WorkoutRecord(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "record") val title: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "diet") val diet: String?,
    @ColumnInfo(name = "desc") val desc: String?
) : java.io.Serializable

