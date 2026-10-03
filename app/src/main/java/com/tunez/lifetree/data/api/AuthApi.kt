package com.tunez.lifetree.data.api

import com.tunez.lifetree.data.model.auth.LoginRequest
import com.tunez.lifetree.data.model.auth.LoginResponse
import com.tunez.lifetree.data.model.auth.RegisterRequest
import com.tunez.lifetree.data.model.auth.RegisterResponse
import com.tunez.lifetree.data.model.user.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("auth/me")
    suspend fun getMe(): User
}