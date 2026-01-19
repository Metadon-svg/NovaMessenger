package com.nova.messenger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nova.messenger.data.models.Message
import com.nova.messenger.data.models.MessageStatus
import com.nova.messenger.ui.theme.*

@Composable
fun MessageBubble(message: Message) {
    val align = if (message.isFromMe) Alignment.End else Alignment.Start
    
    // Форма: Супер-скругленная (Squircle style)
    val shape = RoundedCornerShape(18.dp)

    // СТИЛЬ:
    // Свои: Градиент
    // Чужие: Стекло (Темный фон + тонкая обводка)
    val modifier = if (message.isFromMe) {
        Modifier.background(VisionGradient)
    } else {
        Modifier
            .background(Color(0xFF1F1F1F).copy(alpha = 0.6f)) // Прозрачность
            .border(1.dp, Color.White.copy(alpha = 0.05f), shape) // Стеклянный блик
    }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalAlignment = align) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(shape)
                .then(modifier)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column {
                Text(
                    text = message.text, 
                    color = TextBright, 
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
                
                Row(
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.timestamp, 
                        color = Color.White.copy(alpha = 0.5f), 
                        fontSize = 10.sp
                    )
                    
                    if (message.isFromMe) {
                        Spacer(modifier = Modifier.width(4.dp))
                        val icon = if(message.status == MessageStatus.READ) Icons.Default.DoneAll else Icons.Default.Done
                        val tint = if(message.status == MessageStatus.READ) Color.White else Color.White.copy(alpha = 0.5f)
                        Icon(icon, null, modifier = Modifier.size(12.dp), tint = tint)
                    }
                }
            }
        }
    }
}
