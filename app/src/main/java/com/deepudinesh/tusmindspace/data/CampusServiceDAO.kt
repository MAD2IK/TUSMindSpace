package com.deepudinesh.tusmindspace.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CampusServiceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(services: List<CampusService>)

    @Query("SELECT COUNT(*) FROM campus_services")
    suspend fun count(): Int

    @Query("SELECT * FROM campus_services ORDER BY id ASC")
    fun getAll(): Flow<List<CampusService>>
}