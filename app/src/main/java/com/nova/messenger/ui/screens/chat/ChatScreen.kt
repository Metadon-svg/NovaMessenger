package com.nova.messenger.ui.screens.chat

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.MessageBubble
import com.nova.messenger.ui.theme.*
import com.nova.messenger.utils.FileUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, chatId: String, chatName: String) {
    val context = LocalContext.current
    val allMessages by MockRepository.getMessages(chatId).collectAsState(initial = emptyList())
    val messages = allMessages.filter { it.chatId == chatId }
    
    var text by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    
    var isAttachOpen by remember { mutableStateOf(false) }

    // --- FILE & IMAGE PICKERS ---
    
    // Для картинок
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { MockRepository.sendImage(chatId, it.toString()) }
    }
    
    // Для файлов (Всех типов)
    val fileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            val name = FileUtils.getFileName(context, it)
            val size = FileUtils.getFileSize(context, it)
            MockRepository.sendFile(chatId, it.toString(), name, size)
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    val bgModifier = Modifier.fillMaxSize().background(CleanChatBg)

    Scaffold(
        containerColor = Color.Transparent, 
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().background(CleanSurface)) {
                TopAppBar(
                    title = { 
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(AccentBlue), contentAlignment = Alignment.Center) {
                                 Text(chatName.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(chatName, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                                Text("online", style = MaterialTheme.typography.bodySmall, color = AccentBlue)
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
        Box(modifier = bgModifier.padding(padding)) {
            
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(messages) { msg -> MessageBubble(msg) }
            }

            // ATTACH MENU
            AnimatedVisibility(
                visible = isAttachOpen,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 90.dp)
            ) {
                AttachMenu(onItemClick = { type ->
                    isAttachOpen = false
                    when(type) {
                        "Gallery" -> imageLauncher.launch("image/*")
                        "File" -> fileLauncher.launch("*/*") // Любой файл
                        else -> Toast.makeText(context, "$type click", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            // INPUT
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(CleanSurface)
            ) {
                Row(modifier = Modifier.fillMaxSize().padding(horizontal = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { isAttachOpen = !isAttachOpen }) { 
                        Icon(Icons.Default.Add, null, tint = if(isAttachOpen) AccentBlue else TextGray, modifier = Modifier.rotate(if(isAttachOpen) 45f else 0f)) 
                    }
                    Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp), contentAlignment = Alignment.CenterStart) {
                        if (text.isEmpty()) Text("Message", color = TextGray, fontSize = 16.sp, modifier = Modifier.offset(y = (-1).dp))
                        TextField(
                            value = text, onValueChange = { text = it }, modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = AccentBlue
                            ),
                            singleLine = true, contentPadding = PaddingValues(0.dp)
                        )
                    }
                    if (text.isNotBlank()) {
                         IconButton(onClick = { MockRepository.sendMessage(chatId, text); text = "" }) {
                             Icon(Icons.Default.Send, null, tint = AccentBlue)
                        }
                    } else {
                        IconButton(onClick = {}) { Icon(Icons.Default.Mic, null, tint = TextGray) }
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
        colors = CardDefaults.cardColors(containerColor = CleanSurface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                AttachItem(Icons.Default.Image, "Gallery", Color(0xFF6DC5E3), onItemClick)
                AttachItem(Icons.Default.InsertDriveFile, "File", Color(0xFF8E79B5), onItemClick)
                AttachItem(Icons.Default.LocationOn, "Location", Color(0xFF5BAA61), onItemClick)
            }
        }
    }
}

@Composable
fun AttachItem(icon: ImageVector, label: String, color: Color, onClick: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick(label) }) {
        Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(color), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = Color.White)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 12.sp, color = TextWhite)
    }
}
