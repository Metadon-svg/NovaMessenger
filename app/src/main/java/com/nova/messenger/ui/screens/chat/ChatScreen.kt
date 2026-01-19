package com.nova.messenger.ui.screens.chat

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.MessageBubble
import com.nova.messenger.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, chatId: String, chatName: String) {
    val context = LocalContext.current
    val allMessages by MockRepository.getMessages(chatId).collectAsState(initial = emptyList())
    val messages = allMessages.filter { it.chatId == chatId }
    
    var text by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    
    // UI States
    var isAttachOpen by remember { mutableStateOf(false) }
    var isRecording by remember { mutableStateOf(false) }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    // ОБОИ: Глубокий космос (Deep Space)
    val wallpaperBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF020024), 
            Color(0xFF090979), 
            Color(0xFF000000)
        )
    )

    Scaffold(
        containerColor = Color.Transparent, 
        topBar = {
            // GLASS HEADER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f)) // Легкое затемнение
            ) {
                // Blur Effect simulation via border and alpha
                Box(modifier = Modifier.matchParentSize().border(0.5.dp, Color.White.copy(0.05f)))
                
                TopAppBar(
                    title = { 
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Градиентная аватарка
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(VisionGradient), 
                                contentAlignment = Alignment.Center
                            ) {
                                 Text(chatName.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(chatName, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                                Text("online", style = MaterialTheme.typography.bodySmall, color = NeonBlue)
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) { Icon(Icons.Default.Call, null, tint = Color.White) }
                        IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null, tint = Color.White) }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        }
    ) { padding ->
        // ФОН
        Box(modifier = Modifier.fillMaxSize().background(wallpaperBrush).padding(padding)) {
            
            // СООБЩЕНИЯ
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 100.dp), // Место под Floating Bar
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { msg -> MessageBubble(msg) }
            }

            // --- LIQUID GLASS INPUT (ПАРЯЩАЯ КАПСУЛА) ---
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 12.dp, vertical = 24.dp) // Большой отступ снизу
                    .fillMaxWidth()
                    .height(60.dp) // Высокая капсула
                    .clip(RoundedCornerShape(30.dp)) // Полный круг
                    .background(Color(0xFF101010).copy(alpha = 0.75f)) // Полупрозрачность
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(30.dp)) // Блик
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Кнопка вложений (Слева, с градиентом при нажатии)
                    IconButton(onClick = { isAttachOpen = !isAttachOpen }) { 
                        Icon(
                            Icons.Default.Add, 
                            null, 
                            tint = if(isAttachOpen) NeonBlue else TextMuted,
                            modifier = Modifier
                                .size(28.dp)
                                .background(if(isAttachOpen) Color.White.copy(0.1f) else Color.Transparent, CircleShape)
                                .rotate(if(isAttachOpen) 45f else 0f)
                        ) 
                    }

                    // Поле ввода
                    Box(modifier = Modifier.weight(1f)) {
                        if (text.isEmpty() && !isRecording) {
                            Text("Message...", color = TextMuted, fontSize = 16.sp, modifier = Modifier.padding(start = 8.dp))
                        }
                        
                        TextField(
                            value = text, onValueChange = { text = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = NeonBlue, focusedTextColor = Color.White, unfocusedTextColor = Color.White
                            ),
                            singleLine = true
                        )
                    }
                    
                    if (text.isBlank()) {
                        IconButton(onClick = { /* Camera */ }) { 
                             Icon(Icons.Outlined.CameraAlt, null, tint = TextMuted) 
                        }
                        // Mic Button (Neon)
                        IconButton(onClick = { isRecording = !isRecording }) { 
                            Icon(Icons.Default.Mic, null, tint = if(isRecording) Color.Red else TextMuted) 
                        }
                    } else {
                        // SEND BUTTON (NEON GLOW)
                        IconButton(
                            onClick = { MockRepository.sendMessage(chatId, text); text = "" },
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(VisionGradient)
                        ) {
                             Icon(Icons.Default.ArrowUpward, null, tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}
