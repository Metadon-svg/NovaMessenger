package com.nova.messenger.data.repository

import com.nova.messenger.data.models.*
import com.nova.messenger.utils.TimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MockRepository {
    private val _currentUser = MutableStateFlow(User("0", "Me", "", true, ""))
    val currentUser = _currentUser.asStateFlow()

    private val _chats = MutableStateFlow(
        listOf(Chat("1", "Saved Messages", "Document.pdf", "17:48", 0, true, isPinned = true))
    )
    val chats = _chats.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    fun getMessages(chatId: String) = _messages.asStateFlow()

    fun login(username: String) { _currentUser.update { it.copy(username = username) } }
    fun updateProfile(name: String, bio: String) { _currentUser.update { it.copy(username = name, bio = bio) } }

    fun sendMessage(chatId: String, text: String) {
        addMessage(Message(System.currentTimeMillis().toString(), chatId, text, true, TimeUtils.getCurrentTime()))
    }

    fun sendImage(chatId: String, uri: String) {
        addMessage(Message(
            id = System.currentTimeMillis().toString(), chatId = chatId, text = "", isFromMe = true,
            timestamp = TimeUtils.getCurrentTime(), type = MessageType.IMAGE, mediaUrl = uri
        ))
    }

    // НОВАЯ ФУНКЦИЯ: ОТПРАВКА ФАЙЛА
    fun sendFile(chatId: String, uri: String, name: String, size: String) {
        addMessage(Message(
            id = System.currentTimeMillis().toString(), chatId = chatId, text = "", isFromMe = true,
            timestamp = TimeUtils.getCurrentTime(), type = MessageType.FILE, 
            mediaUrl = uri, fileName = name, fileSize = size
        ))
    }

    private fun addMessage(msg: Message) {
        _messages.update { it + msg }
    }
}
