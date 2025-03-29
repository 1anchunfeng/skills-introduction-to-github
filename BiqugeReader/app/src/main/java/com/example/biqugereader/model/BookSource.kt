package com.example.biqugereader.model

data class BookSource(
    val sourceName: String,
    val baseUrl: String,
    val priority: Int = 1,
    val isEnabled: Boolean = true,
    val searchPath: String = "/s",
    val chapterListPath: String = "/chapterList",
    val contentPath: String = "/content"
)