package com.nova.messenger.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nova.messenger.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var notifications by remember { mutableStateOf(true) }
    var biometrics by remember { mutableStateOf(false) }
    var darkTheme by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            
            SettingsItem(
                icon = Icons.Default.Notifications, 
                title = "Notifications", 
                subtitle = "Sound, Vibrate"
            ) {
                Switch(checked = notifications, onCheckedChange = { notifications = it })
            }
            
            SettingsItem(
                icon = Icons.Default.Lock, 
                title = "Privacy & Security", 
                subtitle = "Biometrics lock"
            ) {
                Switch(checked = biometrics, onCheckedChange = { biometrics = it })
            }

            SettingsItem(
                icon = Icons.Default.DarkMode, 
                title = "Dark Mode", 
                subtitle = "Adaptive theme"
            ) {
                Switch(checked = darkTheme, onCheckedChange = { darkTheme = it })
            }
            
            Divider()
            
            TextButton(
                onClick = { 
                    navController.navigate("login") {
                        popUpTo(0) // Clear stack
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Log Out", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, trailing: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        trailing()
    }
}
