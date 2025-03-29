package com.example.biqugereader.model

import java.util.Date

data class ReaderStatsEntity(
    val chapterId: String,
    val entryTime: Date,
    val exitTime: Date,
    val scrollDistance: Int,
    val jumpFrom: String?,
    val readSpeedWPM: Int
)