package com.nova.messenger.data.models
data class Message(
    val id: String,
    val text: String,
    val isFromMe: Boolean,
    val timestamp: String
)
