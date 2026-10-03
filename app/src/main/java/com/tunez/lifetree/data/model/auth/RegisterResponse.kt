package com.tunez.lifetree.data.model.auth

import com.tunez.lifetree.data.model.user.User
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val id: Int,
    val username: String,
    val email: String,
    val createdAt: String
)