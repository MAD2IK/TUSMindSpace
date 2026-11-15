package com.deepudinesh.tusmindspace.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quote: Quote)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(quotes: List<Quote>)

    @Delete
    suspend fun delete(quote: Quote)

    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun count(): Int

    @Query("SELECT * FROM quotes ORDER BY id ASC")
    fun getAllQuotes(): Flow<List<Quote>>
}