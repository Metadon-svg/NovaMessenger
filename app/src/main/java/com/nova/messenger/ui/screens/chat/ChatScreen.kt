package com.nova.messenger.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.nova.messenger.data.models.MessageStatus
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.theme.BlueGradient
import com.nova.messenger.ui.theme.DarkGray
import com.nova.messenger.utils.TimeUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, chatId: String, chatName: String) {
    // ВАЖНО: collectAsState для реактивности
    val allMessages by MockRepository.getMessages(chatId).collectAsState(initial = emptyList())
    // Фильтруем сообщения конкретно для этого чата
    val messages = allMessages.filter { it.chatId == chatId }
    
    var text by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(chatName, style = MaterialTheme.typography.titleMedium)
                        Text("online", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Call, "Call") }
                    IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, "More") }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) { Icon(Icons.Default.AttachFile, "Attach", tint = Color.Gray) }
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Message") },
                        shape = RoundedCornerShape(20.dp),
                        maxLines = 4
                    )
                    IconButton(onClick = {
                        if (text.isNotBlank()) {
                            MockRepository.sendMessage(chatId, text)
                            text = ""
                        }
                    }) {
                        Icon(Icons.Default.Send, "Send", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(padding).fillMaxSize().padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(messages) { msg ->
                MessageBubble(msg)
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    val align = if (message.isFromMe) Alignment.End else Alignment.Start
    val bg = if (message.isFromMe) BlueGradient else androidx.compose.ui.graphics.SolidColor(DarkGray)
    val shape = if (message.isFromMe) RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp) 
               else RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = align) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(shape)
                .background(bg)
                .padding(10.dp)
        ) {
            Column {
                Text(message.text, color = Color.White)
                Row(modifier = Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
                    Text(message.timestamp, color = Color.White.copy(0.7f), fontSize = 10.sp)
                    if (message.isFromMe) {
                        Spacer(modifier = Modifier.width(4.dp))
                        val icon = when(message.status) {
                            MessageStatus.SENT -> Icons.Default.Check
                            MessageStatus.DELIVERED -> Icons.Default.DoneAll
                            MessageStatus.READ -> Icons.Default.DoneAll
                        }
                        val tint = if(message.status == MessageStatus.READ) Color(0xFF53EDC3) else Color.White.copy(0.7f)
                        Icon(icon, null, modifier = Modifier.size(12.dp), tint = tint)
                    }
                }
            }
        }
    }
}
