package com.nova.messenger
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.nova.messenger.ui.theme.NovaMessengerTheme
import com.nova.messenger.ui.screens.home.HomeScreen
import com.nova.messenger.ui.screens.chat.ChatScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovaMessengerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController) }
                    composable(
                        "chat/{chatId}/{chatName}",
                        arguments = listOf(
                            navArgument("chatId") { type = NavType.StringType },
                            navArgument("chatName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
                        val chatName = backStackEntry.arguments?.getString("chatName") ?: ""
                        ChatScreen(navController, chatId, chatName)
                    }
                }
            }
        }
    }
}
