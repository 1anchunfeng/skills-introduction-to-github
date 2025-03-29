package com.example.biqugereader.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookShelfDao {
    @Insert
    suspend fun insert(book: BookShelfEntity)

    @Update
    suspend fun update(book: BookShelfEntity)

    @Query("DELETE FROM book_shelf WHERE bookId = :bookId")
    suspend fun deleteByBookId(bookId: String)

    @Query("SELECT * FROM book_shelf ORDER BY lastReadTime DESC")
    fun getAllBooks(): Flow<List<BookShelfEntity>>

    @Query("SELECT * FROM book_shelf WHERE status = :status")
    fun getBooksByStatus(status: Int): Flow<List<BookShelfEntity>>

    @Query("SELECT * FROM book_shelf WHERE bookId = :bookId LIMIT 1")
    suspend fun getBookById(bookId: String): BookShelfEntity?
}