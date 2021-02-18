package com.rengwuxian.wecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rengwuxian.wecompose.R
import com.rengwuxian.wecompose.WeViewModel
import com.rengwuxian.wecompose.data.Chat
import com.rengwuxian.wecompose.data.Msg
import com.rengwuxian.wecompose.data.User
import com.rengwuxian.wecompose.ui.theme.WeTheme
import com.rengwuxian.wecompose.unread

@Composable
fun ChatListTopBar() {
  WeTopBar(title = "扔信")
}

@Preview(showBackground = true)
@Composable
fun ChatListTopBarPreview() {
  ChatListTopBar()
}

@Composable
fun ChatListItem(
  chat: Chat,
  modifier: Modifier = Modifier,
  viewModel: WeViewModel = viewModel(),
) {
  Row(
    Modifier
      .fillMaxWidth()
      .clickable(onClick = {
        viewModel.startChat(chat)
      })
  ) {
    Image(
      painterResource(chat.friend.avatar), "avatar", Modifier
        .padding(12.dp, 8.dp, 8.dp, 8.dp)
        .size(48.dp)
        .unread(chat.msgs.last().read, WeTheme.colors.badge)
        .clip(RoundedCornerShape(4.dp))
    )
    Column(
      Modifier
        .weight(1f)
        .align(Alignment.CenterVertically)
    ) {
      Text(chat.friend.name, fontSize = 17.sp, color = WeTheme.colors.textPrimary)
      Text(chat.msgs.last().text, fontSize = 14.sp, color = WeTheme.colors.textSecondary)
    }
    Text(
      "13:48",
      Modifier.padding(8.dp, 8.dp, 12.dp, 8.dp),
      fontSize = 11.sp, color = WeTheme.colors.textSecondary
    )
  }
}

@Composable
fun ChatList(viewModel: WeViewModel = viewModel()) {
  Column(Modifier.fillMaxSize()) {
    ChatListTopBar()
    Box(
      Modifier
        .background(WeTheme.colors.background)
        .fillMaxSize()
    ) {
      ChatList(chats = viewModel.chats)
    }
  }
}

@Composable
fun ChatList(chats: List<Chat>) {
  LazyColumn(
    Modifier
      .background(WeTheme.colors.listItem)
      .fillMaxWidth()
  ) {
    itemsIndexed(chats) { index, chat ->
      ChatListItem(chat)
      if (index < chats.size - 1) {
        Divider(startIndent = 68.dp, color = WeTheme.colors.chatListDivider, thickness = 0.8f.dp)
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ChatListItemPreview() {
  WeTheme {
    Box {
      ChatListItem(
        Chat(
          friend = User("gaolaoshi", "高老师", R.drawable.avatar_1),
          mutableListOf(Msg(User("gaolaoshi", "高老师", R.drawable.avatar_1), "哈哈"))
        ),
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ChatListPreview() {
  val chats = listOf<Chat>(
    Chat(
      friend = User("gaolaoshi", "高老师", R.drawable.avatar_1),
      mutableListOf(Msg(User("gaolaoshi", "高老师", R.drawable.avatar_1), "哈哈"))
    ),
    Chat(
      friend = User("diuwuxian", "丢物线", R.drawable.avatar_2),
      mutableListOf(Msg(User("gaolaoshi", "丢物线", R.drawable.avatar_2), "笑个屁"))
    ),
  )
  ChatList(chats = chats)
}