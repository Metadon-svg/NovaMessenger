package com.nova.messenger.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate // <--- ВОТ ЭТОГО НЕ ХВАТАЛО
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nova.messenger.data.models.Message
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.MessageBubble
import com.nova.messenger.ui.theme.*

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

    // ТЕМНЫЕ ОБОИ (Градиент)
    val wallpaperBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0F1724), 
            Color(0xFF1E2833), 
            Color(0xFF121212)
        )
    )

    Scaffold(
        containerColor = Color.Transparent, 
        topBar = {
            // Полупрозрачный хедер (Glass)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TgSurface.copy(alpha = 0.85f))
            ) {
                TopAppBar(
                    title = { 
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(TgBlue), contentAlignment = Alignment.Center) {
                                 Text(chatName.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(chatName, style = MaterialTheme.typography.titleMedium, color = TgTextMain, fontWeight = FontWeight.Bold)
                                Text("был(а) в 13:44", style = MaterialTheme.typography.bodySmall, color = TgTextSec)
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        }
    ) { padding ->
        // ФОН И КОНТЕНТ
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(wallpaperBrush)
                .padding(padding)
        ) {
            // СПИСОК СООБЩЕНИЙ
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 90.dp), 
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(messages) { msg ->
                    MessageBubble(msg)
                }
            }

            // ПЛАВАЮЩЕЕ ПОЛЕ ВВОДА ("LIQUID GLASS")
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFF1E1E1E).copy(alpha = 0.85f))
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(28.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Кнопка GIF
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(6.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, TgTextSec, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("GIF", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TgTextSec)
                    }

                    // Поле ввода
                    Box(modifier = Modifier.weight(1f)) {
                        if (text.isEmpty()) {
                            Text(
                                "Сообщение", 
                                color = TgTextSec, 
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = TgBlue,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            singleLine = true
                        )
                    }

                    // Иконки справа
                    IconButton(onClick = {}) { 
                        // Теперь rotate работает, так как мы добавили импорт
                        Icon(Icons.Default.AttachFile, null, tint = TgTextSec, modifier = Modifier.rotate(45f)) 
                    }
                    
                    if (text.isBlank()) {
                        IconButton(onClick = {}) { 
                            Icon(Icons.Outlined.CameraAlt, null, tint = TgTextSec) 
                        }
                    } else {
                        IconButton(
                            onClick = {
                                MockRepository.sendMessage(chatId, text)
                                text = ""
                            }
                        ) {
                             Icon(Icons.Default.Send, null, tint = TgBlue)
                        }
                    }
                }
            }
        }
    }
}
