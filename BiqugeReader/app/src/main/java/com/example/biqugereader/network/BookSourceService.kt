package com.example.biqugereader.network

import com.example.biqugereader.model.BookSource
import retrofit2.http.GET
import retrofit2.http.Query

interface BookSourceService {
    @POST("/oauth/token")
    suspend fun login(
        @Query("grant_type") grantType: String = "password",
        @Query("username") username: String,
        @Query("password") password: String
    ): AuthResponse

    @POST("/oauth/register")
    suspend fun register(
        @Body user: RegistrationRequest
    ): AuthResponse

    @POST("/oauth/refresh")
    suspend fun refreshToken(
        @Query("refresh_token") refreshToken: String
    ): AuthResponse
    @GET("/sources")
    suspend fun getAvailableSources(): List<BookSource>

    @GET("/search")
    suspend fun searchBooks(
        @Query("keyword") keyword: String,
        @Query("source") sourceId: String
    ): List<Book>

    @GET("/search/multi")
    suspend fun searchBooksMultiSource(
        @Query("keyword") keyword: String,
        @Query("sources") sourceIds: List<String>,
        @Query("priorityStrategy") strategy: String
    ): List<Book>

    @GET("/books")
    suspend fun getAllBooks(): List<Book>
}