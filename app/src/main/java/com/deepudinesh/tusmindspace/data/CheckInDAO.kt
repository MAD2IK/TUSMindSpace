package com.deepudinesh.tusmindspace.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckInDao {
    @Insert
    suspend fun insert(checkIn: CheckIn)

    @Query("SELECT * FROM check_ins ORDER BY createdAt DESC")
    fun getAll(): Flow<List<CheckIn>>
}