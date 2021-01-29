package com.rengwuxian.wecompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rengwuxian.wecompose.data.Chat

class WeViewModel: ViewModel() {
  var chatting : Boolean by mutableStateOf(false)
  var currentChat: Chat? by mutableStateOf(null)
    private set

  fun startChat(chat: Chat) {
    currentChat = chat
    chatting = true
  }

  fun endChat() {
    chatting = false
  }
}