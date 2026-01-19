package com.nova.messenger.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.ChatItem
import com.nova.messenger.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val chats by MockRepository.chats.collectAsState()
    
    // Эффект градиента сверху (чтобы статус бар читался)
    val topGradient = Brush.verticalGradient(
        colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent)
    )

    Scaffold(
        containerColor = Color.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(64.dp) // Большая кнопка
            ) {
                Icon(Icons.Default.Edit, "New", tint = Color.White)
            }
        },
        bottomBar = {
            // GLASSMORPHISM BOTTOM BAR
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(35.dp)) // Сильное скругление
                    .background(Color(0xFF1A1A1A).copy(alpha = 0.85f)) // Полупрозрачный фон
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) { 
                        Icon(Icons.Filled.ChatBubble, null, tint = MaterialTheme.colorScheme.primary) 
                    }
                    IconButton(onClick = { navController.navigate(Screen.Profile.route) }) { 
                        Icon(Icons.Outlined.Person, null, tint = Color.Gray) 
                    }
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) { 
                        Icon(Icons.Outlined.Settings, null, tint = Color.Gray) 
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(top = 80.dp, bottom = 100.dp) // Отступы под бары
            ) {
                // Заголовок
                item {
                    Text(
                        "Messages",
                        modifier = Modifier.padding(start = 24.dp, bottom = 16.dp),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                }
                items(chats) { chat ->
                    ChatItem(chat) { 
                        navController.navigate(Screen.Chat.createRoute(chat.id, chat.username)) 
                    }
                }
            }
            
            // Тень под статус баром
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(topGradient)
                    .align(Alignment.TopCenter)
            )
            
            // Поиск поверх всего
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(end = 16.dp)
            ) {
                IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search", tint = Color.White) }
            }
        }
    }
}
