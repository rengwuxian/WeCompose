package com.rengwuxian.wecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rengwuxian.wecompose.data.Chat
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

@Composable
fun ChatList(chats: List<Chat>, onChatClick: (Chat) -> Unit) {
  Column(Modifier
    .background(WeComposeTheme.colors.background)
    .fillMaxSize()) {
    WeTopBar("扔信")
    LazyColumn(Modifier.background(WeComposeTheme.colors.listItem)) {
      itemsIndexed(chats) { index, chat ->
        if (index > 0) {
          HorizontalDivider(
            Modifier.padding(start = 68.dp),
            color = WeComposeTheme.colors.divider,
            thickness = 0.8f.dp
          )
        }
        ChatListItem(chat, Modifier.clickable { onChatClick(chat) })
      }
    }
  }
}

@Composable
private fun ChatListItem(chat: Chat, modifier: Modifier = Modifier) {
  Row(
    modifier.fillMaxWidth()
  ) {
    Image(
      painterResource(chat.friend.avatar), chat.friend.name,
      Modifier
        .padding(8.dp)
        .size(48.dp)
        .unread(!chat.msgs.last().read, WeComposeTheme.colors.badge)
        .clip(RoundedCornerShape(4.dp))
    )
    Column(
      Modifier
        .weight(1f)
        .align(Alignment.CenterVertically)
    ) {
      Text(chat.friend.name, fontSize = 17.sp, color = WeComposeTheme.colors.textPrimary)
      Text(chat.msgs.last().text, fontSize = 14.sp, color = WeComposeTheme.colors.textSecondary)
    }
    Text(
      chat.msgs.last().time,
      Modifier.padding(8.dp, 8.dp, 12.dp, 8.dp),
      fontSize = 11.sp, color = WeComposeTheme.colors.textSecondary
    )
  }
}

fun Modifier.unread(show: Boolean, color: Color) = drawWithContent {
  drawContent()
  if (show) {
    drawCircle(color, 5.dp.toPx(), Offset(size.width - 1.dp.toPx(), 1.dp.toPx()))
  }
}