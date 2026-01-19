package com.nova.messenger.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nova.messenger.data.models.Message
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.MessageBubble
import com.nova.messenger.ui.theme.* // Tg цвета

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, chatId: String, chatName: String) {
    val allMessages by MockRepository.getMessages(chatId).collectAsState(initial = emptyList())
    val messages = allMessages.filter { it.chatId == chatId }
    
    var text by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    Scaffold(
        containerColor = TgBg, // Темный фон
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(TgBlue), contentAlignment = Alignment.Center) {
                             Text(chatName.take(1), color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(chatName, style = MaterialTheme.typography.titleMedium, color = TgTextMain)
                            Text("online", style = MaterialTheme.typography.bodySmall, color = TgBlue)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = TgTextMain)
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Call, null, tint = TgTextMain) }
                    IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null, tint = TgTextMain) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TgSurface)
            )
        },
        bottomBar = {
            // Панель ввода как в Telegram
            Column {
                Divider(color = Color.Black, thickness = 0.5.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TgSurface)
                        .padding(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconButton(onClick = {}) { Icon(Icons.Default.SentimentSatisfied, null, tint = TgTextSec) }
                    
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Message", color = TgTextSec) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = TgTextMain,
                            unfocusedTextColor = TgTextMain
                        ),
                        maxLines = 4
                    )
                    
                    if (text.isBlank()) {
                        IconButton(onClick = {}) { Icon(Icons.Default.AttachFile, null, tint = TgTextSec) }
                        IconButton(onClick = {}) { Icon(Icons.Default.Mic, null, tint = TgTextSec) }
                    } else {
                        IconButton(onClick = {
                            MockRepository.sendMessage(chatId, text)
                            text = ""
                        }) {
                            Icon(Icons.Default.Send, "Send", tint = TgBlue)
                        }
                    }
                }
            }
        }
    ) { padding ->
        // Фоновая картинка (паттерн) симулируется цветом
        Box(modifier = Modifier.fillMaxSize().background(TgBg).padding(padding)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(messages) { msg ->
                    MessageBubble(msg)
                }
            }
        }
    }
}
