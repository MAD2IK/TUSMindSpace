package com.deepudinesh.tusmindspace.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quoteText: String,
    val quoteAuthor: String,
    val message: String,
    val createdAt: Long = System.currentTimeMillis()
)