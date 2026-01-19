package com.nova.messenger.data.repository
import com.nova.messenger.data.models.Chat
import com.nova.messenger.data.models.Message
import kotlinx.coroutines.delay

object MockRepository {
    fun getChats(): List<Chat> = listOf(
        Chat("1", "Pavel Durov", "Telegram is simpler", "14:20", 2, true),
        Chat("2", "Elon Musk", "Rocket launched! 🚀", "12:00", 0, false),
        Chat("3", "Saved Messages", "File_2024.pdf", "Yesterday", 0, true),
        Chat("4", "Android Team", "New SDK available", "Mon", 5, true)
    )

    fun getMessages(chatId: String): List<Message> = listOf(
        Message("1", "Hello! How are you?", false, "10:00"),
        Message("2", "Hi! I am doing great, building Nova.", true, "10:05"),
        Message("3", "That sounds awesome! 🚀", false, "10:06"),
        Message("4", "Check this design out.", true, "10:10")
    )
}
