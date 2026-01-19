package com.nova.messenger.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nova.messenger.data.repository.MockRepository
import com.nova.messenger.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val user by MockRepository.currentUser.collectAsState()
    
    // Локальное состояние для редактирования
    var name by remember { mutableStateOf(user.username) }
    var bio by remember { mutableStateOf(user.bio) }

    Scaffold(
        containerColor = TgBg,
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        // СОХРАНЯЕМ ДАННЫЕ В РЕПОЗИТОРИЙ
                        MockRepository.updateProfile(name, bio)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Check, "Save", tint = TgBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TgSurface, titleContentColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Секция аватара
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TgSurface)
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(TgBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(name.take(1).uppercase(), fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    // Кнопка камеры
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(TgSurface)
                            .clickable { /* Upload photo logic mock */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CameraAlt, null, tint = TgTextSec, modifier = Modifier.size(20.dp))
                    }
                }
            }

            Divider(color = Color.Black, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Поля ввода (Как в Телеграм)
            ProfileField(label = "Name", value = name, onValueChange = { name = it })
            Divider(color = Color.Black, thickness = 0.5.dp, modifier = Modifier.padding(start = 16.dp))
            ProfileField(label = "Bio", value = bio, onValueChange = { bio = it })
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                "Username", 
                color = TgBlue, 
                fontWeight = FontWeight.Bold, 
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth()
            )
            Text(
                "@${user.username.replace(" ", "").lowercase()}", 
                color = Color.White, 
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().background(TgSurface).padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(label, color = TgBlue, fontSize = 12.sp)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().padding(0.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true
        )
    }
}
