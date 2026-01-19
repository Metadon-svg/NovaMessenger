package com.nova.messenger.data.models

enum class MessageStatus { SENT, DELIVERED, READ }
enum class MessageType { TEXT, IMAGE, FILE }

data class Message(
    val id: String,
    val chatId: String,
    val text: String,
    val isFromMe: Boolean,
    val timestamp: String,
    val status: MessageStatus = MessageStatus.SENT,
    val type: MessageType = MessageType.TEXT,
    val replyToId: String? = null
)
