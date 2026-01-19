package com.nova.messenger.ui.screens.home
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.ChatItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val chats = MockRepository.getChats()
    
    // Эффект появления при загрузке
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Menu, "Menu") }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            itemsIndexed(chats) { index, chat ->
                // Простая анимация появления (эмуляция staggered)
                ChatItem(chat) { 
                    navController.navigate("chat/${chat.id}/${chat.username}") 
                }
                HorizontalDivider(
                    modifier = Modifier.padding(start = 82.dp), 
                    thickness = 0.5.dp, 
                    color = Color.Gray.copy(alpha = 0.2f)
                )
            }
        }
    }
}
