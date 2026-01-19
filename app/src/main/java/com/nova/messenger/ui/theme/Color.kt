package com.nova.messenger.ui.theme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// --- CLEAN DARK PALETTE ---
val CleanBg = Color(0xFF0F1115)           // Благородный черный (фон списка)
val CleanSurface = Color(0xFF1C1F26)      // Фон панелей
val CleanChatBg = Color(0xFF0E1621)       // Классический темный фон чата Telegram
val AccentBlue = Color(0xFF3390EC)        // Telegram Blue

val TextWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFF788290)

// Пузырьки
val BubbleMy = Color(0xFF3390EC)          // Мой (Синий)
val BubbleOther = Color(0xFF1F2936)       // Чужой (Темный)

// Градиенты убраны в пользу чистого цвета, но оставлены переменные для совместимости
val VisionGradient = Brush.linearGradient(listOf(AccentBlue, AccentBlue))

// --- COMPATIBILITY ---
val TgBg = CleanBg
val TgSurface = CleanSurface
val TgBlue = AccentBlue
val TgTextMain = TextWhite
val TgTextSec = TextGray
val TgItem = Color.Transparent 
val BadgeColor = AccentBlue
val MutedBadge = Color(0xFF353D48)
val TgGreen = Color(0xFF49B669)

val BubbleSelf = BubbleMy
val BubbleOtherColor = BubbleOther // Renamed to avoid conflict
