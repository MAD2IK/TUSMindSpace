package com.deepudinesh.tusmindspace.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_ins")
data class CheckIn(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mood: Int,
    val createdAt: Long = System.currentTimeMillis()
)