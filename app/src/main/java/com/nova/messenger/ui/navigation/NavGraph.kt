package com.nova.messenger.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Chat : Screen("chat/{chatId}/{chatName}") {
        fun createRoute(id: String, name: String) = "chat/$id/$name"
    }
}
