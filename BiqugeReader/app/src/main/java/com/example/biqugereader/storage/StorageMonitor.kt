package com.example.biqugereader.storage

import android.content.Context
import java.io.File
import java.util.concurrent.TimeUnit

class StorageMonitor(private val context: Context) {
    // 支持的文件扩展名集合
    private val supportedExtensions = setOf("epub", "txt")

    /**
     * 清理过期文件
     * @param maxAgeDays 文件最大保留天数（默认1000天）
     */
    fun cleanupOldFiles(maxAgeDays: Int = 1000) {
        val cacheDir = context.cacheDir
        val currentTime = System.currentTimeMillis()
        val maxAgeMillis = TimeUnit.DAYS.toMillis(maxAgeDays.toLong())

        cacheDir.listFiles()?.forEach { file ->
            if (isSupportedFile(file) && currentTime - file.lastModified() > maxAgeMillis) {
                file.delete()
            }
        }
    }

    /**
     * 验证文件类型
     */
    private fun isSupportedFile(file: File): Boolean {
        return file.isFile() && supportedExtensions.any {
            file.name.toLowerCase().endsWith(".$it")
        }
    }
}