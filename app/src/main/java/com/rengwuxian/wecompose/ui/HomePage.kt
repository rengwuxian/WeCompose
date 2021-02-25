package com.rengwuxian.wecompose.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rengwuxian.wecompose.R
import com.rengwuxian.wecompose.WeViewModel
import com.rengwuxian.wecompose.percentOffsetX
import com.rengwuxian.wecompose.ui.theme.WeTheme

@Composable
fun HomeBottomBar(current: Int, currentChanged: (Int) -> Unit) {
  WeBottomBar {
    HomeBottomItem(
      Modifier
        .weight(1f)
        .clickable { currentChanged(0) },
      if (current == 0) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined,
      "聊天",
      if (current == 0) WeTheme.colors.iconCurrent else WeTheme.colors.icon
    )
    HomeBottomItem(
      Modifier
        .weight(1f)
        .clickable { currentChanged(1) },
      if (current == 1) R.drawable.ic_contacts_filled else R.drawable.ic_contacts_outlined,
      "通讯录",
      if (current == 1) WeTheme.colors.iconCurrent else WeTheme.colors.icon
    )
    HomeBottomItem(
      Modifier
        .weight(1f)
        .clickable { currentChanged(2) },
      if (current == 2) R.drawable.ic_discover_filled else R.drawable.ic_discover_outlined,
      "发现",
      if (current == 2) WeTheme.colors.iconCurrent else WeTheme.colors.icon
    )
    HomeBottomItem(
      Modifier
        .weight(1f)
        .clickable { currentChanged(3) },
      if (current == 3) R.drawable.ic_me_filled else R.drawable.ic_me_outlined,
      "我",
      if (current == 3) WeTheme.colors.iconCurrent else WeTheme.colors.icon
    )
  }
}

@Composable
fun HomeBottomItem(
  modifier: Modifier = Modifier,
  @DrawableRes iconId: Int,
  title: String,
  tint: Color
) {
  Column(
    modifier.padding(0.dp, 8.dp, 0.dp, 8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Icon(painterResource(iconId), null, Modifier.size(24.dp), tint = tint)
    Text(title, fontSize = 11.sp, color = tint)
  }
}

@Preview(showBackground = true)
@Composable
fun WeBottomPreview() {
  HomeBottomBar(0) {}
}

@Composable
fun Home() {
  val viewModel: WeViewModel = viewModel()
  Box {
    Column(Modifier.fillMaxSize()) {
      val pagerState: PagerState = run {
        remember(viewModel.theme) { PagerState(maxPage = 3) }
      }
      Pager(pagerState, Modifier.weight(1f)) {
        when (page) {
          0 -> {
            ChatList()
          }
          1 -> {
            ContactList()
          }
          2 -> {
            DiscoveryList()
          }
          3 -> {
            MeList()
          }
        }
      }
      HomeBottomBar(pagerState.currentPage) {
        pagerState.currentPage = it
      }
    }
    val openOffset by animateFloatAsState(
      if (viewModel.openModule == null) {
        1f
      } else {
        0f
      }
    )
    if (viewModel.currentChat != null) {
      ChatPage(
        Modifier.percentOffsetX(openOffset),
        chat = viewModel.currentChat
      ) {
        viewModel.endChat()
      }
    }
  }
}