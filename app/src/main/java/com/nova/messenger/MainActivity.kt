package com.nova.messenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nova.messenger.ui.theme.NovaMessengerTheme
import com.nova.messenger.ui.navigation.Screen
import com.nova.messenger.ui.screens.auth.LoginScreen
import com.nova.messenger.ui.screens.home.HomeScreen
import com.nova.messenger.ui.screens.profile.ProfileScreen
import com.nova.messenger.ui.screens.settings.SettingsScreen
import com.nova.messenger.ui.screens.chat.ChatScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovaMessengerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Login.route) {
                    
                    composable(Screen.Login.route) { 
                        LoginScreen(navController) 
                    }
                    
                    composable(Screen.Home.route) { 
                        HomeScreen(navController) 
                    }
                    
                    composable(Screen.Profile.route) { 
                        ProfileScreen(navController) 
                    }
                    
                    composable(Screen.Settings.route) { 
                        SettingsScreen(navController) 
                    }

                    composable(
                        Screen.Chat.route,
                        arguments = listOf(
                            navArgument("chatId") { type = NavType.StringType },
                            navArgument("chatName") { type = NavType.StringType }
                        )
                    ) { entry ->
                        val id = entry.arguments?.getString("chatId") ?: ""
                        val name = entry.arguments?.getString("chatName") ?: ""
                        ChatScreen(navController, id, name)
                    }
                }
            }
        }
    }
}
