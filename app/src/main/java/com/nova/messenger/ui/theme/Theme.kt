package com.nova.messenger.ui.theme
import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val TelegramScheme = darkColorScheme(
    primary = TgBlue,
    onPrimary = TgTextMain,
    background = TgBg,
    surface = TgSurface,
    onBackground = TgTextMain,
    onSurface = TgTextMain,
    surfaceVariant = TgSurface
)

@Composable
fun NovaMessengerTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Цвет статус бара как у хедера (Surface)
            window.statusBarColor = TgSurface.toArgb() 
            window.navigationBarColor = TgBg.toArgb()
            
            val insets = WindowCompat.getInsetsController(window, view)
            insets.isAppearanceLightStatusBars = false
            insets.isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = TelegramScheme,
        content = content
    )
}
