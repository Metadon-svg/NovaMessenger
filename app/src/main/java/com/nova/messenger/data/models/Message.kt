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
    val mediaUrl: String? = null, // URI файла или картинки
    val fileName: String? = null, // Имя файла (Presentation.pdf)
    val fileSize: String? = null  // Размер файла (2.5 MB)
)
