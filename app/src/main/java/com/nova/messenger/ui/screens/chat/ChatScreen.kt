package com.nova.messenger.ui.screens.chat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nova.messenger.data.models.Message
import com.nova.messenger.data.repository.MockRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, chatId: String, chatName: String) {
    val messages = remember { mutableStateListOf<Message>().apply { addAll(MockRepository.getMessages(chatId)) } }
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(chatName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(modifier = Modifier.padding(8.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Message") },
                    shape = RoundedCornerShape(20.dp)
                )
                IconButton(onClick = {
                    if (text.isNotBlank()) {
                        messages.add(Message("new", text, true, "Now"))
                        text = ""
                    }
                }) {
                    Icon(Icons.Default.Send, "Send", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().padding(horizontal = 8.dp),
            reverseLayout = false
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
    val color = if (message.isFromMe) Color(0xFF0088CC) else Color(0xFF2C2C2E)
    val shape = if (message.isFromMe) RoundedCornerShape(12.dp, 12.dp, 0.dp, 12.dp) 
               else RoundedCornerShape(12.dp, 12.dp, 12.dp, 0.dp)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = align) {
        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clip(shape)
                .background(color)
                .padding(10.dp)
        ) {
            Text(message.text, color = Color.White)
        }
    }
}
