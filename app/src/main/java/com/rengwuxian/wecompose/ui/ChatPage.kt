package com.rengwuxian.wecompose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rengwuxian.wecompose.data.Chat

@Composable
fun WeChat(modifier: Modifier = Modifier, chat: Chat?, onBack: (() -> Unit)) {
  if (chat != null) {
    Scaffold(
      modifier,
      topBar = {
        WeTopBar(chat.friend.name, onBack)
      },
      bottomBar = {
        WeBottomBar()
      }
    ) {
        LazyColumn(content = {
          items(chat.msgs) { msg ->
            Row {
              Text(msg.from.name)
              Text(msg.text)
            }
          }
        })
    }
  }
}

@Composable
fun WeChatHomeBar() {

}

@Composable
fun MessageItem() {
  LazyColumn(content = { /*TODO*/ })
}