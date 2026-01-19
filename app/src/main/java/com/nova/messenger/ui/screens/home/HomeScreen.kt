package com.nova.messenger.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.ChatItem
import com.nova.messenger.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val chats by MockRepository.chats.collectAsState()
    val user by MockRepository.currentUser.collectAsState()
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = TgSurface,
                drawerContentColor = TgTextMain,
                modifier = Modifier.width(300.dp)
            ) {
                // DRAWER HEADER
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF232E3C)) // Slightly lighter header
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(TgBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        // Avatar image mock
                        Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(user.username, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(user.bio, color = TgTextSec, fontSize = 14.sp)
                }
                
                Divider(color = Color.Black, thickness = 0.5.dp)

                // DRAWER ITEMS
                DrawerItem(Icons.Default.Group, "New Group")
                DrawerItem(Icons.Default.Person, "Contacts")
                DrawerItem(Icons.Default.Call, "Calls")
                DrawerItem(Icons.Default.Bookmark, "Saved Messages")
                DrawerItem(Icons.Default.Settings, "Settings")
                
                Divider(color = Color.Black, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))
                
                DrawerItem(Icons.Default.PersonAdd, "Invite Friends")
                DrawerItem(Icons.Default.Help, "Telegram Features")
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nova", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search") }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = TgSurface,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    containerColor = TgBlue,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Edit, "New Chat")
                }
            },
            containerColor = TgBg
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(chats) { chat ->
                    ChatItem(chat) { 
                        // Navigate to chat
                    }
                    Divider(color = Color.Black, thickness = 0.5.dp, modifier = Modifier.padding(start = 76.dp))
                }
            }
        }
    }
}

@Composable
fun DrawerItem(icon: ImageVector, text: String) {
    NavigationDrawerItem(
        label = { Text(text, color = Color.White) },
        selected = false,
        onClick = {},
        icon = { Icon(icon, null, tint = Color.Gray) },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent
        ),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
    )
}
