package com.nova.messenger.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import com.nova.messenger.ui.navigation.Screen
import com.nova.messenger.ui.theme.* // Импорт Tg цветов

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }

    // Фон Telegram Dark
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TgBg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            // Логотип (Круглый синий с самолетиком или буквой)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(TgBlue),
                contentAlignment = Alignment.Center
            ) {
                // Имитация бумажного самолетика
                Icon(
                    Icons.Default.ArrowForward, 
                    contentDescription = null, 
                    tint = Color.White,
                    modifier = Modifier.size(50.dp).rotate(-45f) 
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Nova Messenger",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TgTextMain
            )
            
            Text(
                "Please enter your username",
                fontSize = 14.sp,
                color = TgTextSec,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Поле ввода в стиле Telegram
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TgTextMain,
                    unfocusedTextColor = TgTextMain,
                    focusedBorderColor = TgBlue,
                    unfocusedBorderColor = TgSurface,
                    focusedLabelColor = TgBlue,
                    unfocusedLabelColor = TgTextSec,
                    containerColor = Color.Transparent,
                    cursorColor = TgBlue
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Кнопка
            Button(
                onClick = { 
                    MockRepository.login(username.ifBlank { "User" })
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TgBlue)
            ) {
                Text("Start Messaging", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Хелпер для поворота иконки (если еще нет)
fun androidx.compose.ui.Modifier.rotate(degrees: Float) = this.then(
    androidx.compose.ui.Modifier.graphicsLayer(rotationZ = degrees)
)
