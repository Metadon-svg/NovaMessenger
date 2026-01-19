package com.nova.messenger.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nova.messenger.data.models.Message
import com.nova.messenger.data.models.MessageStatus
import com.nova.messenger.ui.theme.*

@Composable
fun MessageBubble(message: Message) {
    val align = if (message.isFromMe) Alignment.End else Alignment.Start
    
    // Obsidian Colors
    val backgroundColor = if (message.isFromMe) BubbleSelf else BubbleOther
    val textColor = TextPrimary
    
    // Modern shape: G2-like curvature
    val shape = if (message.isFromMe) {
        RoundedCornerShape(topStart = 20.dp, topEnd = 4.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
    } else {
        RoundedCornerShape(topStart = 4.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp), // Minimal vertical spacing
        horizontalAlignment = align
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(shape)
                .background(backgroundColor)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column {
                Text(
                    text = message.text, 
                    color = textColor, 
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
                
                // Meta info (Time + Status)
                Row(
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.timestamp, 
                        color = Color.White.copy(alpha = 0.5f), 
                        fontSize = 11.sp
                    )
                    
                    if (message.isFromMe) {
                        Spacer(modifier = Modifier.width(4.dp))
                        val icon = if(message.status == MessageStatus.READ) Icons.Default.DoneAll else Icons.Default.Done
                        val tint = if(message.status == MessageStatus.READ) Color.White else Color.White.copy(alpha = 0.5f)
                        
                        Icon(icon, null, modifier = Modifier.size(14.dp), tint = tint)
                    }
                }
            }
        }
    }
}
