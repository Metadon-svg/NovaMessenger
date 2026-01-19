package com.nova.messenger.ui.theme
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Глубокий черный для AMOLED
private val UltraDarkScheme = darkColorScheme(
    primary = Color(0xFF3390EC), // Telegram Blue
    secondary = Color(0xFF00E5FF),
    background = Color(0xFF000000), // Pure Black
    surface = Color(0xFF101010), // Soft Black
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun NovaMessengerTheme(
    darkTheme: Boolean = true, // ВСЕГДА ТЕМНАЯ (For Premium Feel)
    content: @Composable () -> Unit
) {
    val colorScheme = UltraDarkScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // ДЕЛАЕМ СТАТУС БАР ПРОЗРАЧНЫМ
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb() // И нижнюю полосу тоже
            
            // Белые иконки (время, батарея)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
