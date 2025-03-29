package com.example.biqugereader.features.reader

import com.example.biqugereader.model.ReaderStatsEntity
import java.util.Date

class ReadingProgress {
    private var startTime: Date? = null
    private var wordCount: Int = 0
    
    /**
     * 计算每分钟阅读字数
     */
    fun calculateReadSpeed(stats: ReaderStatsEntity): Int {
        val durationMinutes = (stats.exitTime.time - stats.entryTime.time) / 60000.0
        return if (durationMinutes > 0) (wordCount / durationMinutes).toInt() else 0
    }

    /**
     * 开始阅读计时
     */
    fun startReadingSession(totalWords: Int) {
        startTime = Date()
        wordCount = totalWords
    }

    /**
     * 结束阅读会话并生成统计
     */
    fun endReadingSession(chapterId: String, jumpFrom: String?): ReaderStatsEntity {
        val endTime = Date()
        return ReaderStatsEntity(
            chapterId = chapterId,
            entryTime = startTime ?: Date(),
            exitTime = endTime,
            scrollDistance = 0,
            jumpFrom = jumpFrom,
            readSpeedWPM = calculateReadSpeed(
                ReaderStatsEntity(
                    chapterId,
                    startTime ?: Date(),
                    endTime,
                    0,
                    jumpFrom,
                    0
                )
            )
        )
    }
}