package com.nova.messenger.ui.screens.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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

    // Gradient Background (Subtle Deep Blue to Black)
    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF050A14), // Very deep blue top
            Color(0xFF000000)  // Pitch black bottom
        )
    )

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            // Glass Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ObsidianBg.copy(alpha = 0.7f)) // Semi-transparent
            ) {
                // Separator line
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(Color.White.copy(alpha = 0.1f))
                )
                
                TopAppBar(
                    title = { 
                        Column {
                            Text(
                                chatName, 
                                style = MaterialTheme.typography.titleMedium, 
                                color = TextPrimary, 
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "Online", 
                                style = MaterialTheme.typography.bodySmall, 
                                color = AccentBlue
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, "Back", tint = TextPrimary)
                        }
                    },
                    actions = {
                        // Minimalistic actions
                        IconButton(onClick = {}) { 
                            Box(
                                modifier = Modifier.size(36.dp).clip(CircleShape).background(ObsidianSurfaceHighlight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.MoreHoriz, null, tint = TextPrimary, modifier = Modifier.size(20.dp)) 
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgBrush)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp), // More breathing room
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + 8.dp, 
                    bottom = 100.dp
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp) // Tighter messages
            ) {
                items(messages) { msg ->
                    MessageBubble(msg)
                }
            }

            // --- OBSIDIAN INPUT BAR ---
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp) // Floating
                    .navigationBarsPadding() // Respect gestures
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(28.dp)) // Super rounded
                        .background(ObsidianSurfaceHighlight) // Lighter than bg
                        .border(0.5.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(28.dp)), // Glass border
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    
                    // Attach Button (Plus icon)
                    IconButton(
                        onClick = {},
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Icon(Icons.Outlined.Add, null, tint = TextSecondary)
                    }

                    // Input Field
                    Box(modifier = Modifier.weight(1f)) {
                        if (text.isEmpty()) {
                            Text(
                                "Message...", 
                                color = TextSecondary, 
                                fontSize = 16.sp,
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
                                cursorColor = AccentBlue,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            singleLine = true
                        )
                    }

                    // Dynamic Action Button
                    AnimatedVisibility(
                        visible = text.isNotBlank(),
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        IconButton(
                            onClick = {
                                MockRepository.sendMessage(chatId, text)
                                text = ""
                            },
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(AccentBlue)
                        ) {
                            Icon(Icons.Default.ArrowUpward, null, tint = Color.White)
                        }
                    }
                    
                    AnimatedVisibility(
                        visible = text.isBlank(),
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Row {
                             IconButton(onClick = {}) { 
                                Icon(Icons.Outlined.CameraAlt, null, tint = TextSecondary) 
                            }
                        }
                    }
                }
            }
        }
    }
}
