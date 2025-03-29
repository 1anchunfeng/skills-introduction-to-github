package com.example.biqugereader.manager

import android.content.Context
import androidx.core.content.edit
import com.example.biqugereader.network.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserSessionManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    suspend fun login(authResponse: AuthResponse) = withContext(Dispatchers.IO) {
。        sharedPreferences.edit {
            putString("access_token", authResponse.accessToken)
            putString("refresh_token", authResponse.refreshToken)
            putLong("expires_in", System.currentTimeMillis() + authResponse.expiresIn * 1000)
        }
    }

    suspend fun refreshToken(newToken: String) = withContext(Dispatchers.IO) {
        sharedPreferences.edit {
            putString("access_token", newToken)
        }
    }

    fun getAccessToken(): String? = sharedPreferences.getString("access_token", null)

    fun isLoggedIn(): Boolean {
        return sharedPreferences.contains("access_token") && 
               sharedPreferences.getLong("expires_in", 0) > System.currentTimeMillis()
    }

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}