package com.nova.messenger.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nova.messenger.data.models.Message
import com.nova.messenger.ui.theme.BlueGradient
import com.nova.messenger.ui.theme.DarkGray

@Composable
fun MessageBubble(message: Message) {
    val align = if (message.isFromMe) Alignment.End else Alignment.Start
    
    // Используем градиент для своих сообщений и сплошной цвет для чужих
    val backgroundModifier = if (message.isFromMe) {
        Modifier.background(BlueGradient)
    } else {
        Modifier.background(DarkGray)
    }

    val shape = if (message.isFromMe) {
        RoundedCornerShape(18.dp, 18.dp, 2.dp, 18.dp)
    } else {
        RoundedCornerShape(18.dp, 18.dp, 18.dp, 2.dp)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = align
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(shape)
                .then(backgroundModifier)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column {
                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = message.timestamp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                )
            }
        }
    }
}
