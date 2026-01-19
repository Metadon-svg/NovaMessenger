package com.nova.messenger.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.ChatItem
import com.nova.messenger.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val chats by MockRepository.chats.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var activeTab by remember { mutableStateOf(0) } // 0=Chats, 1=Profile, 2=Settings

    // Filter chats
    val filteredChats = chats.filter { 
        it.username.contains(searchQuery, ignoreCase = true) || 
        it.lastMessage.contains(searchQuery, ignoreCase = true) 
    }

    Scaffold(
        topBar = {
            if (activeTab == 0) {
                TopAppBar(
                    title = { Text("Nova") },
                    actions = {
                        IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search") }
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    icon = { Icon(Icons.Filled.ChatBubble, "Chats") },
                    label = { Text("Chats") }
                )
                NavigationBarItem(
                    selected = activeTab == 1,
                    onClick = { 
                        navController.navigate(Screen.Profile.route) 
                        activeTab = 0 // Reset for demo simplicity
                    },
                    icon = { Icon(Icons.Filled.Person, "Profile") },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = activeTab == 2,
                    onClick = { 
                        navController.navigate(Screen.Settings.route)
                        activeTab = 0
                    },
                    icon = { Icon(Icons.Filled.Settings, "Settings") },
                    label = { Text("Settings") }
                )
            }
        },
        floatingActionButton = {
            if (activeTab == 0) {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Default.Edit, "New Chat")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Search Bar (Optional visibility)
            if (activeTab == 0) {
                LazyColumn {
                    items(filteredChats, key = { it.id }) { chat ->
                        
                        // Swipe to delete capability implementation requires extra library or complex code.
                        // For simplicity in this script, we use the standard click.
                        // In a real app, SwipeToDismissBox (Material3) would be here.
                        
                        ChatItem(chat) { 
                            navController.navigate(Screen.Chat.createRoute(chat.id, chat.username)) 
                        }
                        Divider(modifier = Modifier.padding(start = 70.dp), thickness = 0.5.dp, color = Color.Gray.copy(alpha = 0.2f))
                    }
                }
            }
        }
    }
}
