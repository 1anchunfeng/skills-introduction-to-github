package com.example.biqugereader.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

abstract class BaseRepository {
    protected fun buildClient(vararg interceptors: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .apply {
                // 添加通用错误拦截器
                addInterceptor(NetworkErrorInterceptor())
                interceptors.forEach { addInterceptor(it) }
            }
            .build()
    }

    protected inline fun <reified T> createService(
        baseUrl: String,
        vararg interceptors: Interceptor
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(buildClient(*interceptors))
            .build()
            .create(T::class.java)
    }
}

class NetworkErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            // 处理通用错误码
            when (response.code) {
                401 -> throw AuthException("身份验证失败")
                500 -> throw ServerException("服务器内部错误")
                // 其他错误处理...
            }
        }
        return response
    }
}

data class AuthException(override val message: String?) : Exception(message)
data class ServerException(override val message: String?) : Exception(message)