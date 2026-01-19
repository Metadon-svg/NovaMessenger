package com.nova.messenger.ui.theme
import androidx.compose.ui.graphics.Color

// --- OBSIDIAN PALETTE ---
val ObsidianBg = Color(0xFF000000)         // Абсолютно черный
val ObsidianSurface = Color(0xFF141414)    // Едва заметный серый
val ObsidianSurfaceHighlight = Color(0xFF1E1E1E) 
val AccentBlue = Color(0xFF2979FF)         // Электрик синий
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFF8E8E93)      
val DividerColor = Color(0xFF2C2C2E)

// Цвета пузырьков
val BubbleSelf = Color(0xFF2979FF)
val BubbleOther = Color(0xFF1C1C1E)

// --- COMPATIBILITY MAPPING (FIX) ---
val TgBlue = AccentBlue
val TgBg = ObsidianBg
val TgSurface = ObsidianSurface
val TgTextMain = TextPrimary
val TgTextSec = TextSecondary
val TgGreen = Color(0xFF30D158)

// Возвращаем переменные для ChatItem.kt, адаптированные под Obsidian стиль
val TgItem = ObsidianBg       // Фон элемента чата теперь черный
val BadgeColor = AccentBlue   // Бейдж сообщений синий
val MutedBadge = Color(0xFF3A3A3C) // Muted бейдж темно-серый
