package com.nova.messenger.ui.screens.chat

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, chatId: String, chatName: String) {
    val context = LocalContext.current
    val allMessages by MockRepository.getMessages(chatId).collectAsState(initial = emptyList())
    val messages = allMessages.filter { it.chatId == chatId }
    
    var text by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    
    // States for functional menus
    var isMenuExpanded by remember { mutableStateOf(false) }
    var isAttachOpen by remember { mutableStateOf(false) }
    var isRecording by remember { mutableStateOf(false) }

    // Scroll to bottom
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    val wallpaperBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0F1724), Color(0xFF1E2833), Color(0xFF121212))
    )

    Scaffold(
        containerColor = Color.Transparent, 
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().background(TgSurface.copy(alpha = 0.95f))) {
                TopAppBar(
                    title = { 
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                            // Имитация перехода в профиль
                            Toast.makeText(context, "Open Profile: $chatName", Toast.LENGTH_SHORT).show()
                        }) {
                            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(TgBlue), contentAlignment = Alignment.Center) {
                                 Text(chatName.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(chatName, style = MaterialTheme.typography.titleMedium, color = TgTextMain, fontWeight = FontWeight.Bold)
                                Text(if(isRecording) "Recording audio..." else "online", style = MaterialTheme.typography.bodySmall, color = if(isRecording) Color.Red else TgBlue)
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, "Back", tint = TgTextMain)
                        }
                    },
                    actions = {
                        IconButton(onClick = { Toast.makeText(context, "Calling $chatName...", Toast.LENGTH_SHORT).show() }) { 
                            Icon(Icons.Default.Call, null, tint = TgTextMain) 
                        }
                        
                        // Dropdown Menu (Three dots)
                        Box {
                            IconButton(onClick = { isMenuExpanded = true }) { Icon(Icons.Default.MoreVert, null, tint = TgTextMain) }
                            DropdownMenu(
                                expanded = isMenuExpanded,
                                onDismissRequest = { isMenuExpanded = false },
                                modifier = Modifier.background(TgSurface)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Search", color = Color.White) },
                                    onClick = { isMenuExpanded = false; Toast.makeText(context, "Search in chat", Toast.LENGTH_SHORT).show() },
                                    leadingIcon = { Icon(Icons.Default.Search, null, tint = TgTextSec) }
                                )
                                DropdownMenuItem(
                                    text = { Text("Mute", color = Color.White) },
                                    onClick = { isMenuExpanded = false; Toast.makeText(context, "Notifications muted", Toast.LENGTH_SHORT).show() },
                                    leadingIcon = { Icon(Icons.Outlined.NotificationsOff, null, tint = TgTextSec) }
                                )
                                DropdownMenuItem(
                                    text = { Text("Clear History", color = Color.Red) },
                                    onClick = { isMenuExpanded = false; Toast.makeText(context, "History cleared", Toast.LENGTH_SHORT).show() },
                                    leadingIcon = { Icon(Icons.Outlined.Delete, null, tint = Color.Red) }
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(wallpaperBrush).padding(padding)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 90.dp), 
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(messages) { msg -> MessageBubble(msg) }
            }

            // ATTACHMENT MENU (Animated)
            AnimatedVisibility(
                visible = isAttachOpen,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp)
            ) {
                AttachMenu(onItemClick = { 
                    isAttachOpen = false
                    Toast.makeText(context, "Attached: $it", Toast.LENGTH_SHORT).show()
                })
            }

            // LIQUID INPUT BAR
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFF1E1E1E).copy(alpha = 0.95f))
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(28.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // GIF Button
                    Box(modifier = Modifier.size(40.dp).padding(6.dp).clip(CircleShape).border(1.5.dp, TgTextSec, CircleShape).clickable { Toast.makeText(context, "GIF Keyboard", Toast.LENGTH_SHORT).show() }, contentAlignment = Alignment.Center) {
                        Text("GIF", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TgTextSec)
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (text.isEmpty() && !isRecording) Text("Message", color = TgTextSec, modifier = Modifier.padding(start = 8.dp))
                        if (isRecording) Text("Recording... 0:02", color = Color.Red, modifier = Modifier.padding(start = 8.dp))
                        
                        if (!isRecording) {
                            TextField(
                                value = text, onValueChange = { text = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = TgBlue, focusedTextColor = Color.White, unfocusedTextColor = Color.White
                                ),
                                singleLine = true
                            )
                        }
                    }

                    // Attach Icon (Toggle Menu)
                    IconButton(onClick = { isAttachOpen = !isAttachOpen }) { 
                        Icon(Icons.Default.AttachFile, null, tint = if(isAttachOpen) TgBlue else TgTextSec, modifier = Modifier.rotate(45f)) 
                    }
                    
                    if (text.isBlank()) {
                        // Mic Icon (Hold to record simulation - Tap to toggle)
                        IconButton(onClick = { isRecording = !isRecording }) { 
                            Icon(Icons.Default.Mic, null, tint = if(isRecording) Color.Red else TgTextSec) 
                        }
                    } else {
                        IconButton(onClick = { MockRepository.sendMessage(chatId, text); text = "" }) {
                             Icon(Icons.Default.Send, null, tint = TgBlue)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AttachMenu(onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = TgSurface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                AttachItem(Icons.Default.Image, "Gallery", Color(0xFF6DC5E3), onItemClick)
                AttachItem(Icons.Default.InsertDriveFile, "File", Color(0xFF8E79B5), onItemClick)
                AttachItem(Icons.Default.LocationOn, "Location", Color(0xFF5BAA61), onItemClick)
            }
            Spacer(modifier = Modifier.height(16.dp))
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
            modifier = Modifier.size(50.dp).clip(CircleShape).background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Color.White)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 12.sp, color = Color.White)
    }
}
