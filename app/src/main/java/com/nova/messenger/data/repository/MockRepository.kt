package com.nova.messenger.data.repository

import com.nova.messenger.data.models.*
import com.nova.messenger.utils.TimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MockRepository {

    private val _currentUser = MutableStateFlow(
        User("0", "SpeedSilver | TRT", "+375 (33) 342-56-83", true, "Online")
    )
    val currentUser = _currentUser.asStateFlow()

    private val _chats = MutableStateFlow(
        listOf(
            Chat("1", "Saved Messages", "Video_2024.mp4", "17:48", 0, true, isPinned = true),
            Chat("2", "PornHub Premium", "New content uploaded 🔥", "17:48", 13, false, isPinned = true),
            Chat("3", "Mcd Team", "Bot: Order #2139 ready", "14:20", 1, true),
            Chat("4", "Aprel Mods", "Update v7.2 released", "Yesterday", 32, false),
            Chat("5", "Standoff 2", "New Season Pass!", "Dec 31", 0, true, isPinned = true),
            Chat("6", "BotFather", "New bot created.", "Dec 25", 0, true),
            Chat("7", "Mom", "Buy some milk please", "10:00", 2, true)
        )
    )
    val chats = _chats.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())

    fun login(username: String) {
        _currentUser.update { it.copy(username = username) }
    }

    fun updateProfile(name: String, bio: String) {
        _currentUser.update { it.copy(username = name, bio = bio) }
    }

    fun getMessages(chatId: String) = _messages.asStateFlow()

    fun sendMessage(chatId: String, text: String) {
        val newMessage = Message(
            id = System.currentTimeMillis().toString(),
            chatId = chatId,
            text = text,
            isFromMe = true,
            timestamp = TimeUtils.getCurrentTime(),
            status = MessageStatus.SENT
        )
        _messages.update { it + newMessage }
    }
}
