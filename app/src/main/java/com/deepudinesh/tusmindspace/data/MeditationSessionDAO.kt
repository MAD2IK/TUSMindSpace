package com.deepudinesh.tusmindspace.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeditationSessionDao {
    @Insert
    suspend fun insert(session: MeditationSession)

    @Query("SELECT * FROM meditation_sessions ORDER BY completedAt DESC LIMIT 10")
    fun getRecent(): Flow<List<MeditationSession>>
}
