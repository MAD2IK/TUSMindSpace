package com.deepudinesh.tusmindspace.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campus_services")
data class CampusService(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val subtitle: String,
    val icon: String,
    val lat: Double?,
    val lon: Double?
)