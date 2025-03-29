package com.example.biqugereader.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_shelf")
data class BookShelfEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val currentChapter: Int = 1,
    val progress: Float = 0f,
    val addTime: Long = System.currentTimeMillis(),
    val lastReadTime: Long = System.currentTimeMillis(),
    val status: Int = 0 // 0-未读 1-阅读中 2-已读完
)