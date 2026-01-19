package com.nova.messenger.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nova.messenger.data.models.Message
import com.nova.messenger.data.models.MessageType
import com.nova.messenger.ui.theme.*

@Composable
fun MessageBubble(message: Message) {
    val context = LocalContext.current
    val align = if (message.isFromMe) Alignment.End else Alignment.Start
    val bg = if (message.isFromMe) BubbleMy else BubbleOther
    val shape = RoundedCornerShape(16.dp)

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalAlignment = align) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(shape)
                .background(bg)
                .clickable {
                    // Имитация открытия файла
                    if (message.type == MessageType.FILE && message.mediaUrl != null) {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(message.mediaUrl))
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Если нечем открыть
                        }
                    }
                }
        ) {
            Column {
                // --- ЛОГИКА ОТОБРАЖЕНИЯ ---
                
                // 1. КАРТИНКА
                if (message.type == MessageType.IMAGE && message.mediaUrl != null) {
                    AsyncImage(
                        model = message.mediaUrl, contentDescription = null,
                        modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // 2. ФАЙЛ (КАРТОЧКА КАК В ТГ)
                if (message.type == MessageType.FILE) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Иконка файла (Круглая)
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)), // Полупрозрачный фон иконки
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description, // Или Download, если не скачан
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        // Инфо о файле
                        Column {
                            Text(
                                text = message.fileName ?: "Document",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${message.fileSize ?: "Unknown"} • Document",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                // 3. ТЕКСТ (Если есть)
                if (message.text.isNotEmpty()) {
                    Text(
                        text = message.text, color = Color.White, fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                
                // Время (Внизу справа)
                if (message.type != MessageType.IMAGE) { // Для картинок время можно наложить, но для файлов лучше внизу
                     Box(modifier = Modifier.align(Alignment.End).padding(end = 8.dp, bottom = 6.dp)) {
                        Text(message.timestamp, color = Color.White.copy(0.6f), fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
