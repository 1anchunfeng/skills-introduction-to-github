package com.example.biqugereader.cache

import com.example.biqugereader.model.Book
import com.example.biqugereader.network.BookSourceService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object PreloadManager {
    private val memoryCache = mutableMap<String, Book>()

    fun initialize(service: BookSourceService) {
        CoroutineScope(Dispatchers.IO).launch {
            val books = service.getAllBooks()
            books.forEach { book ->
                memoryCache[book.id] = book.copy(
                    chapters = emptyList(),
                    tags = book.tags.take(5)
                )
            }
        }
    }

    fun getCachedBook(id: String): Book? = memoryCache[id]

    fun clearCache() {
        memoryCache.clear()
        System.gc()
    }
}