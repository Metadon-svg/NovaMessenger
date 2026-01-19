package com.nova.messenger.ui.theme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// --- 2026 VISION PALETTE ---
val DeepSpaceBg = Color(0xFF050505)       // Почти черный, но с глубиной
val GlassPanel = Color(0xFF1E1E1E)        // Основа для стекла
val GlassBorder = Color(0xFFFFFFFF)       // Для обводки (с низкой прозрачностью)

val NeonBlue = Color(0xFF2E8AF7)          // Основной акцент
val NeonPurple = Color(0xFF8E2DE2)        // Вторичный акцент

val TextBright = Color(0xFFFFFFFF)
val TextMuted = Color(0xFF8899A6)

// Градиент для своих сообщений (Cyberpunk Blue)
val VisionGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF2E8AF7),
        Color(0xFF4A00E0)
    )
)

// Цвета для совместимости
val TgBg = DeepSpaceBg
val TgSurface = GlassPanel
val TgBlue = NeonBlue
val TgTextMain = TextBright
val TgTextSec = TextMuted
val TgItem = Color.Transparent 
val BadgeColor = NeonBlue
val MutedBadge = Color(0xFF333333)
val TgGreen = Color(0xFF00FF94) // Neon Green
