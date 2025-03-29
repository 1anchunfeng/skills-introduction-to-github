package com.example.biqugereader.model

data class Chapter(
    val id: String,
    val title: String,
    val content: String,
    val chapterNumber: Int,
    val isVip: Boolean = false,
    val updateTime: Long = System.currentTimeMillis()
)