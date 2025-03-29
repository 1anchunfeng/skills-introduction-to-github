package com.example.biqugereader.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val latestChapter: String,
    val intro: String,
    val chapters: List<Chapter> = emptyList(),
    val tags: List<String> = emptyList(),
    val socialComment: List<Comment> = emptyList()
)