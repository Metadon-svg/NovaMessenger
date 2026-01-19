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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nova.messenger.data.models.Message
import com.nova.messenger.data.models.MessageType
import com.nova.messenger.ui.theme.*

@Composable
fun MessageBubble(message: Message) {
    val align = if (message.isFromMe) Alignment.End else Alignment.Start
    val bg = if (message.isFromMe) BubbleMy else BubbleOther
    val shape = RoundedCornerShape(16.dp)

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalAlignment = align) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(shape)
                .background(bg)
        ) {
            Column {
                // ЕСЛИ ЭТО КАРТИНКА
                if (message.type == MessageType.IMAGE && message.imageUrl != null) {
                    AsyncImage(
                        model = message.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // ТЕКСТ (Если есть)
                if (message.text.isNotEmpty()) {
                    Text(
                        text = message.text, 
                        color = Color.White, 
                        fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                
                // ВРЕМЯ (Поверх картинки или внизу текста)
                Box(modifier = Modifier.align(Alignment.End).padding(end = 6.dp, bottom = 4.dp)) {
                    Text(message.timestamp, color = Color.White.copy(0.7f), fontSize = 10.sp)
                }
            }
        }
    }
}
