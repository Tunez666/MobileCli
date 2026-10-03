package com.tunez.lifetree.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val createdAt: String
)