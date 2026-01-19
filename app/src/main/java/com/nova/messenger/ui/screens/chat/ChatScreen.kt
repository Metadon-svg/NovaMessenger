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

    val wallpaperBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF020024), Color(0xFF090979), Color(0xFF000000))
    )

    Scaffold(
        containerColor = Color.Transparent, 
        topBar = {
            // GLASS HEADER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f))
            ) {
                Box(modifier = Modifier.matchParentSize().border(0.5.dp, Color.White.copy(0.05f)))
                
                TopAppBar(
                    title = { 
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(42.dp).clip(CircleShape).background(VisionGradient), contentAlignment = Alignment.Center) {
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
        Box(modifier = Modifier.fillMaxSize().background(wallpaperBrush).padding(padding)) {
            
            // MESSAGES
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 140.dp), // Место под меню
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { msg -> MessageBubble(msg) }
            }

            // --- ATTACHMENT MENU (VISION STYLE) ---
            AnimatedVisibility(
                visible = isAttachOpen,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp) // Над полем ввода
            ) {
                AttachMenu(onItemClick = { 
                    isAttachOpen = false
                    Toast.makeText(context, "Selected: $it", Toast.LENGTH_SHORT).show()
                })
            }

            // --- INPUT CAPSULE ---
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 12.dp, vertical = 24.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF101010).copy(alpha = 0.85f))
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(30.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Attach Button (Plus)
                    IconButton(onClick = { isAttachOpen = !isAttachOpen }) { 
                        Icon(
                            Icons.Default.Add, null, 
                            tint = if(isAttachOpen) NeonBlue else TextMuted,
                            modifier = Modifier
                                .size(28.dp)
                                .rotate(if(isAttachOpen) 45f else 0f)
                        ) 
                    }

                    // Input
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
                        IconButton(onClick = {}) { Icon(Icons.Outlined.CameraAlt, null, tint = TextMuted) }
                        IconButton(onClick = { isRecording = !isRecording }) { 
                            Icon(Icons.Default.Mic, null, tint = if(isRecording) Color.Red else TextMuted) 
                        }
                    } else {
                        IconButton(
                            onClick = { MockRepository.sendMessage(chatId, text); text = "" },
                            modifier = Modifier.size(42.dp).clip(CircleShape).background(VisionGradient)
                        ) {
                             Icon(Icons.Default.ArrowUpward, null, tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}

// --- КРАСИВОЕ МЕНЮ ВЛОЖЕНИЙ ---
@Composable
fun AttachMenu(onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E).copy(alpha = 0.9f)), // Glass
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.1f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                AttachItem(Icons.Default.Image, "Gallery", Color(0xFF6DC5E3), onItemClick)
                AttachItem(Icons.Default.InsertDriveFile, "File", Color(0xFF8E79B5), onItemClick)
                AttachItem(Icons.Default.LocationOn, "Location", Color(0xFF5BAA61), onItemClick)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                AttachItem(Icons.Default.Poll, "Poll", Color(0xFFE4A951), onItemClick)
                AttachItem(Icons.Default.Person, "Contact", Color(0xFF538BB8), onItemClick)
                AttachItem(Icons.Default.MusicNote, "Music", Color(0xFFDD6157), onItemClick)
            }
        }
    }
}

@Composable
fun AttachItem(icon: ImageVector, label: String, color: Color, onClick: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick(label) }) {
        Box(
            modifier = Modifier.size(56.dp).clip(CircleShape).background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontSize = 12.sp, color = Color.White)
    }
}
