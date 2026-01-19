package com.nova.messenger.ui.theme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// --- New Premium Colors ---
val Primary = Color(0xFF2979FF)
val Secondary = Color(0xFF00E5FF)
val BackgroundDark = Color(0xFF000000)
val SurfaceDark = Color(0xFF121212)
val SurfaceLight = Color(0xFFFFFFFF)

// --- Legacy Support (чтобы не ломался старый код) ---
val Tertiary = Color(0xFF53EDC3)
val LightBackground = Color(0xFFFFFFFF)
val DarkBackground = Color(0xFF121212)
val DarkGray = Color(0xFF212121)

// Градиент для фона (Login Screen)
val PremiumGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF141E30),
        Color(0xFF243B55)
    )
)

// Градиент для сообщений (Restored)
val BlueGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF2AABEE),
        Color(0xFF229ED9)
    )
)
