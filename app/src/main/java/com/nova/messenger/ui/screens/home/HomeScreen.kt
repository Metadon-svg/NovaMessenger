package com.nova.messenger.ui.screens.home

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.components.ChatItem
import com.nova.messenger.ui.navigation.Screen
import com.nova.messenger.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val chats by MockRepository.chats.collectAsState()
    val user by MockRepository.currentUser.collectAsState()
    val context = LocalContext.current
    
    // Состояние поиска
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    
    // Фильтрация чатов
    val filteredChats = chats.filter { 
        it.username.contains(searchQuery, ignoreCase = true) || 
        it.lastMessage.contains(searchQuery, ignoreCase = true)
    }
    
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
                // Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF232E3C))
                        .clickable { 
                            navController.navigate(Screen.Profile.route)
                            scope.launch { drawerState.close() }
                        }
                        .padding(16.dp)
                ) {
                    Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(TgBlue), contentAlignment = Alignment.Center) {
                        Text(user.username.take(1), fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(user.username, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(user.bio, color = TgTextSec, fontSize = 14.sp)
                }
                Divider(color = Color.Black, thickness = 0.5.dp)

                // Items with TOAST functionality
                DrawerItem(Icons.Default.Group, "New Group") {
                    Toast.makeText(context, "Create Group feature coming soon", Toast.LENGTH_SHORT).show()
                }
                DrawerItem(Icons.Default.Person, "Contacts") {
                    Toast.makeText(context, "Accessing contacts...", Toast.LENGTH_SHORT).show()
                }
                DrawerItem(Icons.Default.Call, "Calls") {
                    Toast.makeText(context, "Call history empty", Toast.LENGTH_SHORT).show()
                }
                DrawerItem(Icons.Default.Bookmark, "Saved Messages") {
                    navController.navigate(Screen.Chat.createRoute("1", "Saved Messages"))
                    scope.launch { drawerState.close() }
                }
                DrawerItem(Icons.Default.Settings, "Settings") {
                    navController.navigate(Screen.Settings.route)
                    scope.launch { drawerState.close() }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (isSearchActive) {
                    // Search Bar Mode
                    TopAppBar(
                        title = {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search chats...", color = TgTextSec) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { 
                                isSearchActive = false 
                                searchQuery = ""
                            }) {
                                Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = TgSurface)
                    )
                } else {
                    // Normal Mode
                    TopAppBar(
                        title = { Text("Nova", fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, "Menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = { isSearchActive = true }) { 
                                Icon(Icons.Default.Search, "Search") 
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = TgSurface,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White,
                            actionIconContentColor = Color.White
                        )
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { Toast.makeText(context, "New Message", Toast.LENGTH_SHORT).show() },
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
                items(filteredChats) { chat ->
                    ChatItem(chat) { 
                        navController.navigate(Screen.Chat.createRoute(chat.id, chat.username)) 
                    }
                    Divider(color = Color.Black, thickness = 0.5.dp, modifier = Modifier.padding(start = 76.dp))
                }
            }
        }
    }
}

@Composable
fun DrawerItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(text, color = Color.White) },
        selected = false,
        onClick = onClick,
        icon = { Icon(icon, null, tint = Color.Gray) },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
    )
}
