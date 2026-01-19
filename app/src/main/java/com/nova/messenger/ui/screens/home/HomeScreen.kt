package com.nova.messenger.ui.screens.home
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.ChatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val chats = MockRepository.getChats()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova") },
                navigationIcon = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Menu, "Menu") }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search") }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(chats) { chat ->
                ChatItem(chat) { navController.navigate("chat/${chat.id}/${chat.username}") }
            }
        }
    }
}
