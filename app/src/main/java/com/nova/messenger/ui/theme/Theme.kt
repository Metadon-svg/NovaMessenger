package com.nova.messenger.ui.theme
import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ObsidianScheme = darkColorScheme(
    primary = AccentBlue,
    background = ObsidianBg,
    surface = ObsidianSurface,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
)

@Composable
fun NovaMessengerTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Полная прозрачность для Edge-to-Edge
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            
            val insets = WindowCompat.getInsetsController(window, view)
            insets.isAppearanceLightStatusBars = false // Белые иконки
            insets.isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = ObsidianScheme,
        content = content
    )
}
