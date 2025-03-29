package com.example.biqugereader.network.model

data class RegistrationRequest(
    val username: String,
    val password: String,
    val email: String,
    val nickname: String
)