package com.nova.messenger.ui.theme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// --- 2026 VISION PALETTE ---
val DeepSpaceBg = Color(0xFF050505)
val GlassPanel = Color(0xFF1E1E1E)
val GlassBorder = Color(0xFFFFFFFF)

val NeonBlue = Color(0xFF2E8AF7)
val NeonPurple = Color(0xFF8E2DE2)

val TextBright = Color(0xFFFFFFFF)
val TextMuted = Color(0xFF8899A6)

// Gradient
val VisionGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF2E8AF7), Color(0xFF4A00E0))
)

// --- COMPATIBILITY LAYER (ЧТОБЫ НЕ ЛОМАЛАСЬ СБОРКА) ---
// Мы просто перенаправляем старые названия на новые цвета
val AccentBlue = NeonBlue
val ObsidianBg = DeepSpaceBg
val ObsidianSurface = GlassPanel
val TextPrimary = TextBright
val TextSecondary = TextMuted
val DividerColor = Color(0xFF2C2C2E)

val TgBg = DeepSpaceBg
val TgSurface = GlassPanel
val TgBlue = NeonBlue
val TgTextMain = TextBright
val TgTextSec = TextMuted
val TgItem = Color.Transparent 
val BadgeColor = NeonBlue
val MutedBadge = Color(0xFF333333)
val TgGreen = Color(0xFF00FF94) 

// Bubble Colors
val BubbleSelf = NeonBlue // Заглушка, реально используется градиент
val BubbleOther = GlassPanel
