package com.nova.messenger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nova.messenger.data.models.Message
import com.nova.messenger.data.models.MessageStatus
import com.nova.messenger.ui.theme.* // Импортируем новые цвета (TgBlue и т.д.)

@Composable
fun MessageBubble(message: Message) {
    val align = if (message.isFromMe) Alignment.End else Alignment.Start
    
    // FIX: Используем цвета Telegram вместо удаленных Gradient
    val backgroundColor = if (message.isFromMe) TgBlue else TgSurface
    
    val shape = if (message.isFromMe) RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp) 
               else RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = align) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(shape)
                .background(backgroundColor) // Solid color
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Column {
                Text(message.text, color = Color.White, fontSize = 16.sp)
                
                Row(
                    modifier = Modifier.align(Alignment.End).padding(top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        message.timestamp, 
                        color = Color.White.copy(alpha = 0.6f), 
                        fontSize = 11.sp
                    )
                    
                    if (message.isFromMe) {
                        Spacer(modifier = Modifier.width(4.dp))
                        val icon = when(message.status) {
                            MessageStatus.SENT -> Icons.Default.Check
                            MessageStatus.DELIVERED -> Icons.Default.DoneAll
                            MessageStatus.READ -> Icons.Default.DoneAll
                        }
                        // Зеленая галочка если прочитано, иначе белая
                        val tint = if(message.status == MessageStatus.READ) TgGreen else Color.White.copy(0.6f)
                        Icon(icon, null, modifier = Modifier.size(14.dp), tint = tint)
                    }
                }
            }
        }
    }
}
