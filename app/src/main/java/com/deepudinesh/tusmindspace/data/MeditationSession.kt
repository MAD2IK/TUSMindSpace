package com.deepudinesh.tusmindspace.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meditation_sessions")
data class MeditationSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val mins: Int,
    val completedAt: Long = System.currentTimeMillis()
)
