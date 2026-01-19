package com.nova.messenger.ui.theme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF2979FF) // Bright Blue
val Secondary = Color(0xFF00E5FF) // Cyan accent
val BackgroundDark = Color(0xFF000000) // True Black for OLED
val SurfaceDark = Color(0xFF121212) // Slightly lighter for cards
val SurfaceLight = Color(0xFFFFFFFF)

// Градиент для фона (Login Screen)
val PremiumGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF141E30),
        Color(0xFF243B55)
    )
)

val CardGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF1E1E1E),
        Color(0xFF252525)
    )
)
