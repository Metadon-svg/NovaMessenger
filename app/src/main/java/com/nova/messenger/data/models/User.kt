package com.nova.messenger.data.models

data class User(
    val id: String,
    val username: String,
    val bio: String,
    val isOnline: Boolean,
    val lastSeen: String,
    val avatarUrl: String? = null
)
