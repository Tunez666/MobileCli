package com.tunez.lifetree.data.api

import com.tunez.lifetree.data.local.TokenStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiClient {

    private const val BASE_URL =
        "http://195.19.7.5:3000/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private fun createAuthInterceptor(
        tokenStorage: TokenStorage
    ) = Interceptor { chain ->

        val token = runBlocking {
            tokenStorage.accessToken.first()
        }

        val request = chain.request()
            .newBuilder()
            .apply {
                if (!token.isNullOrBlank()) {
                    addHeader(
                        "Authorization",
                        "Bearer $token"
                    )
                }
            }
            .build()

        chain.proceed(request)
    }

    fun createAuthApi(
        tokenStorage: TokenStorage
    ): AuthApi {

        val okHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    createAuthInterceptor(tokenStorage)
                )
                .addInterceptor(loggingInterceptor)
                .build()

        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(
                    json.asConverterFactory(
                        "application/json".toMediaType()
                    )
                )
                .build()

        return retrofit.create(AuthApi::class.java)
    }
}