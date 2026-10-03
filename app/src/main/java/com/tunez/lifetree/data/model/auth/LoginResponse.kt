package com.tunez.lifetree.data.model.auth

import com.tunez.lifetree.data.model.user.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val user: User
)