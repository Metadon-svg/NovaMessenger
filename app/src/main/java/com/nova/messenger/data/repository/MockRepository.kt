package com.nova.messenger.data.repository

import com.nova.messenger.data.models.*
import com.nova.messenger.utils.TimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MockRepository {

    // Текущий пользователь
    private val _currentUser = MutableStateFlow(
        User("0", "Me", "Designer", true, "Online")
    )
    val currentUser = _currentUser.asStateFlow()

    // ТОЛЬКО ОДИН ЧАТ - ИЗБРАННОЕ
    private val _chats = MutableStateFlow(
        listOf(
            Chat(
                id = "saved",
                username = "Saved Messages",
                lastMessage = "Keep your data safe here",
                time = "",
                unreadCount = 0,
                isOnline = true, // Всегда онлайн (синяя точка)
                isPinned = true
            )
        )
    )
    val chats = _chats.asStateFlow()

    // Пустые сообщения (пока пользователь сам не напишет)
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
        
        // Обновляем превью чата
        _chats.update { list ->
            list.map { 
                if (it.id == chatId) it.copy(lastMessage = text, time = newMessage.timestamp) 
                else it 
            }
        }
    }
}
