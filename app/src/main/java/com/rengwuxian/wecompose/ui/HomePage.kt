package com.rengwuxian.wecompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.rengwuxian.wecompose.WeViewModel
import com.rengwuxian.wecompose.data.Chat
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Home

@Composable
fun HomePage(viewModel: WeViewModel, onOpenChat: (Chat) -> Unit) {
  Column(Modifier
    .background(WeComposeTheme.colors.background)
    .statusBarsPadding()) {
    val pagerState = rememberPagerState { 4 }
    HorizontalPager(pagerState, Modifier.weight(1f)) { page ->
      when (page) {
        0 -> ChatList(viewModel.chats, onOpenChat)
        1 -> ContactList(viewModel.contacts)
        2 -> DiscoveryList()
        3 -> MeList()
      }
    }
    val scope = rememberCoroutineScope()
    WeNavigationBar(pagerState.currentPage) {
      scope.launch { pagerState.animateScrollToPage(it) }
    }
  }
}