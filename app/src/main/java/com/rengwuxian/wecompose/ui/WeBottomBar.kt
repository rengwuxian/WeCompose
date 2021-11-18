package com.rengwuxian.wecompose.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

@Composable
fun WeBottomBar(selected: Int, onSelectedChanged: (Int) -> Unit) {
  Row(Modifier.background(WeComposeTheme.colors.bottomBar)) {
    TabItem(
      if (selected == 0) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined, "聊天",
      if (selected == 0) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
      Modifier
        .weight(1f)
        .clickable {
          onSelectedChanged(0)
        }
    )
    TabItem(
      if (selected == 1) R.drawable.ic_contacts_filled else R.drawable.ic_contacts_outlined,
      "通讯录",
      if (selected == 1) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
      Modifier
        .weight(1f)
        .clickable {
          onSelectedChanged(1)
        }
    )
    TabItem(
      if (selected == 2) R.drawable.ic_discovery_filled else R.drawable.ic_discovery_outlined,
      "发现",
      if (selected == 2) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
      Modifier
        .weight(1f)
        .clickable {
          onSelectedChanged(2)
        }
    )
    TabItem(
      if (selected == 3) R.drawable.ic_me_filled else R.drawable.ic_me_outlined, "我",
      if (selected == 3) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
      Modifier
        .weight(1f)
        .clickable {
          onSelectedChanged(3)
        }
    )
  }
}

@Composable
fun TabItem(@DrawableRes iconId: Int, title: String, tint: Color, modifier: Modifier = Modifier) {
  Column(
    modifier.padding(vertical = 8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Icon(painterResource(iconId), title, Modifier.size(24.dp), tint = tint)
    Text(title, fontSize = 11.sp, color = tint)
  }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreview() {
  WeComposeTheme(WeComposeTheme.Theme.Light) {
    var selectedTab by remember { mutableStateOf(0) }
    WeBottomBar(selectedTab) { selectedTab = it }
  }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreviewDark() {
  WeComposeTheme(WeComposeTheme.Theme.Dark) {
    var selectedTab by remember { mutableStateOf(0) }
    WeBottomBar(selectedTab) { selectedTab = it }
  }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreviewNewYear() {
  WeComposeTheme(WeComposeTheme.Theme.NewYear) {
    var selectedTab by remember { mutableStateOf(0) }
    WeBottomBar(selectedTab) { selectedTab = it }
  }
}

@Preview(showBackground = true)
@Composable
fun TabItemPreview() {
  TabItem(iconId = R.drawable.ic_chat_outlined, title = "聊天", tint = WeComposeTheme.colors.icon)
}