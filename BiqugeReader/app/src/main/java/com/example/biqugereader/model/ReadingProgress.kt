package com.example.biqugereader.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_progress")
data class ReadingProgress(
    @PrimaryKey val bookId: String,
    val chapterId: String,
    val progressPosition: Int,
    val notes: String = "",
    val isBookmarked: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)