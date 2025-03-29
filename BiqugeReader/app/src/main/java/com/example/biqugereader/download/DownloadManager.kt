package com.example.biqugereader.download

import com.example.biqugereader.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadManager {
    private var remainingDownloaded = false
    private var readingProgressObserver: ReadingProgressObserver? = null

    fun downloadBook(book: Book) {
        val totalChapters = book.chapters.size
        val initialBatch = if (totalChapters > 500) 500 else totalChapters
        
        downloadChapters(book, 0 until initialBatch)
        
        if (totalChapters > 500) {
            readingProgressObserver = createProgressObserver { currentChapter ->
                if (currentChapter >= 100 && !remainingDownloaded) {
                    downloadChapters(book, 500 until totalChapters)
                    remainingDownloaded = true
                }
            }
        }
    }

    private fun downloadChapters(book: Book, range: IntRange) {
        val chapterBatch = book.chapters.slice(range)
        CoroutineScope(Dispatchers.IO).launch {
            chapterBatch.chunked(50) { pages ->
                pages.forEach { chapter ->
                    // 章节下载实现
                }
                System.gc()
            }
        }
    }

    interface ReadingProgressObserver {
        fun onProgress(currentChapter: Int)
    }

    private fun createProgressObserver(callback: (Int) -> Unit): ReadingProgressObserver {
        return object : ReadingProgressObserver {
            override fun onProgress(currentChapter: Int) {
                callback(currentChapter)
            }
        }
    }
}