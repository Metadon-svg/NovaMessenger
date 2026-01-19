package com.nova.messenger.ui.theme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF0088CC)
val Secondary = Color(0xFF00A2E8)
val Tertiary = Color(0xFF53EDC3)

val DarkBackground = Color(0xFF0F0F0F) // Почти черный, но мягче
val SurfaceDark = Color(0xFF1E1E1E)

val LightBackground = Color(0xFFFFFFFF)
val SurfaceLight = Color(0xFFF5F5F5)

// Градиент для сообщений (как в Telegram/Insta)
val BlueGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF2AABEE),
        Color(0xFF229ED9)
    )
)

val DarkGray = Color(0xFF212121)
