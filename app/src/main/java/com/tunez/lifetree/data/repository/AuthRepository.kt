package com.tunez.lifetree.data.repository

import com.tunez.lifetree.data.api.AuthApi
import com.tunez.lifetree.data.local.TokenStorage
import com.tunez.lifetree.data.model.auth.LoginRequest
import com.tunez.lifetree.data.model.auth.LoginResponse
import com.tunez.lifetree.data.model.auth.RegisterRequest
import com.tunez.lifetree.data.model.auth.RegisterResponse
import com.tunez.lifetree.data.model.user.User
import kotlinx.coroutines.flow.first

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) {

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): RegisterResponse {

        return authApi.register(
            RegisterRequest(
                username = username,
                email = email,
                password = password
            )
        )
    }

    suspend fun login(
        email: String,
        password: String
    ): LoginResponse {

        val response = authApi.login(
            LoginRequest(
                email = email,
                password = password
            )
        )

        tokenStorage.saveToken(response.accessToken)

        return response
    }

    suspend fun getMe(): User {
        return authApi.getMe()
    }

    suspend fun hasToken(): Boolean {
        return !tokenStorage.accessToken.first().isNullOrBlank()
    }

    suspend fun logout() {
        tokenStorage.clearToken()
    }
}