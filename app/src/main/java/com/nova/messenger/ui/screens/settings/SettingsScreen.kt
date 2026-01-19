package com.nova.messenger.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nova.messenger.ui.navigation.Screen
import com.nova.messenger.ui.theme.SurfaceDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var notifications by remember { mutableStateOf(true) }
    var biometrics by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            LargeTopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            // Group 1: General
            SettingsGroup("General") {
                SettingsTile(Icons.Default.Notifications, "Notifications", true) {
                    Switch(checked = notifications, onCheckedChange = { notifications = it })
                }
                Spacer(modifier = Modifier.height(2.dp))
                SettingsTile(Icons.Default.Language, "Language", false)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Group 2: Privacy
            SettingsGroup("Privacy") {
                SettingsTile(Icons.Default.Lock, "Security Code", true) {
                    Switch(checked = biometrics, onCheckedChange = { biometrics = it })
                }
                Spacer(modifier = Modifier.height(2.dp))
                SettingsTile(Icons.Default.Visibility, "Privacy Settings", false)
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            // Logout Button
            Button(
                onClick = { 
                    navController.navigate(Screen.Login.route) { popUpTo(0) } 
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B30)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Log Out", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Text(
        title, 
        color = MaterialTheme.colorScheme.primary, 
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
    )
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        content()
    }
}

@Composable
fun SettingsTile(
    icon: ImageVector, 
    title: String, 
    isLast: Boolean, 
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, modifier = Modifier.weight(1f), fontSize = 16.sp)
        if (trailing != null) {
            trailing()
        } else {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.size(16.dp), tint = Color.Gray) // Chevron
        }
    }
}
