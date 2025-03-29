package com.example.biqugereader.network

import com.example.biqugereader.cache.PreloadManager
import com.example.biqugereader.model.Book
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MetaSearchService(private val sourceService: BookSourceService) {
    
    suspend fun metaSearch(keyword: String): List<Book> {
        val activeSources = sourceService.getAvailableSources()
            .filter { it.status == "active" }
            .take(20)

        return coroutineScope {
            activeSources.map { source ->
                async {
                    try {
                        sourceService.searchBooksMultiSource(
                            keyword = keyword,
                            sourceIds = listOf(source.id),
                            strategy = "quality_first"
                        )
                    } catch (e: Exception) {
                        emptyList<Book>()
                    }
                }
            }.flatMap { it.await() }
        }.filter { book ->
            PreloadManager.getCachedBook(book.id)?.let { cached ->
                cached.chapters.isNotEmpty() && cached.tags.size >= 3
            } ?: false
        }
    }

    fun filterDeadLinks(books: List<Book>): List<Book> {
        return books.distinctBy { it.url }
            .filterNot { it.url.contains("404") }
            .sortedByDescending { it.rating }
    }
}