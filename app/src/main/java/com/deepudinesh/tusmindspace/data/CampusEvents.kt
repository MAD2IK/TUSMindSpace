package com.deepudinesh.tusmindspace.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campus_events")
data class CampusEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val startMillis: Long,
    val location: String,
    val lat: Double,
    val lon: Double
)