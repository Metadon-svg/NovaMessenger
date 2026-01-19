package com.nova.messenger.data.models

data class Chat(
    val id: String,
    val username: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val isOnline: Boolean,
    val isPinned: Boolean = false,
    val isMuted: Boolean = false
)
