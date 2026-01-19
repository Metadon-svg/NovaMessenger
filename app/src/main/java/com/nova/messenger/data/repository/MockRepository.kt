package com.nova.messenger.data.repository

import com.nova.messenger.data.models.*
import com.nova.messenger.utils.TimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MockRepository {

    // --- STATE ---
    private val _currentUser = MutableStateFlow(
        User("0", "My Account", "Android Developer & Architect", true, "Just now")
    )
    val currentUser = _currentUser.asStateFlow()

    private val _chats = MutableStateFlow(
        listOf(
            Chat("1", "Pavel Durov", "Telegram is simpler", "14:20", 2, true, isPinned = true),
            Chat("2", "Elon Musk", "Rocket launched! 🚀", "12:00", 0, false),
            Chat("3", "Saved Messages", "File_2024.pdf", "Yesterday", 0, true),
            Chat("4", "Android Team", "New SDK available", "Mon", 5, true)
        )
    )
    val chats = _chats.asStateFlow()

    private val _messages = MutableStateFlow(
        listOf(
            Message("1", "1", "Hello! How are you?", false, "10:00", MessageStatus.READ),
            Message("2", "1", "Hi! I am doing great, building Nova.", true, "10:05", MessageStatus.READ),
            Message("3", "1", "That sounds awesome! 🚀", false, "10:06", MessageStatus.READ),
            Message("4", "1", "Check this design out.", true, "10:10", MessageStatus.DELIVERED)
        )
    )
    
    // --- ACTIONS ---

    fun login(username: String) {
        _currentUser.update { it.copy(username = username) }
    }

    fun updateProfile(name: String, bio: String) {
        _currentUser.update { it.copy(username = name, bio = bio) }
    }

    fun getMessages(chatId: String): StateFlow<List<Message>> {
        // В реальности здесь был бы фильтр потока БД
        return MutableStateFlow(_messages.value.filter { it.chatId == chatId }).asStateFlow()
    }

    fun sendMessage(chatId: String, text: String) {
        val newMessage = Message(
            id = System.currentTimeMillis().toString(),
            chatId = chatId,
            text = text,
            isFromMe = true,
            timestamp = TimeUtils.getCurrentTime(),
            status = MessageStatus.SENT
        )
        // Добавляем в общий список (упрощенно)
        _messages.update { it + newMessage }
        
        // Обновляем последний текст в чате
        _chats.update { currentChats ->
            currentChats.map { 
                if (it.id == chatId) it.copy(lastMessage = text, time = newMessage.timestamp) 
                else it 
            }
        }
    }

    fun deleteChat(chatId: String) {
        _chats.update { it.filter { chat -> chat.id != chatId } }
    }

    fun togglePin(chatId: String) {
        _chats.update { list ->
            list.map { if (it.id == chatId) it.copy(isPinned = !it.isPinned) else it }
                .sortedByDescending { it.isPinned } // Pinned first
        }
    }
}
