package com.nova.messenger.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nova.messenger.data.models.Chat
import com.nova.messenger.ui.theme.*

@Composable
fun ChatItem(chat: Chat, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(TgItem)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(if(chat.id == "1") TgBlue else Color(0xFFE56555)),
            contentAlignment = Alignment.Center
        ) {
            Text(chat.username.take(1), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        chat.username, 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 16.sp, 
                        color = TgTextMain,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (!chat.isOnline) { // Muted icon simulation
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.VolumeOff, null, modifier = Modifier.size(14.dp), tint = TgTextSec)
                    }
                }
                Text(chat.time, fontSize = 13.sp, color = TgTextSec)
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(
                    chat.lastMessage, 
                    maxLines = 1, 
                    color = TgTextSec,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (chat.isPinned) {
                        Icon(Icons.Default.PushPin, null, modifier = Modifier.size(16.dp).rotate(45f), tint = TgTextSec)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    if (chat.unreadCount > 0) {
                        Box(
                            modifier = Modifier
                                .height(22.dp)
                                .widthIn(min = 22.dp)
                                .clip(CircleShape)
                                .background(if(chat.isOnline) BadgeColor else MutedBadge),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                chat.unreadCount.toString(), 
                                fontSize = 12.sp, 
                                color = Color.White, 
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun androidx.compose.ui.Modifier.rotate(degrees: Float) = this // Stub for rotate if needed
