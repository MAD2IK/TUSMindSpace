package com.deepudinesh.tusmindspace.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CampusEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(events: List<CampusEvent>)

    @Delete
    suspend fun delete(event: CampusEvent)

    @Query("SELECT COUNT(*) FROM campus_events")
    suspend fun count(): Int

    @Query("SELECT * FROM campus_events ORDER BY startMillis ASC")
    fun getAll(): Flow<List<CampusEvent>>

    @Query("SELECT * FROM campus_events WHERE startMillis >= :now ORDER BY startMillis ASC")
    fun getUpcoming(now: Long): Flow<List<CampusEvent>>
}