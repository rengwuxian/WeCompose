package com.rengwuxian.wecompose.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rengwuxian.wecompose.R
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

@Composable
fun WeNavigationBar(selected: Int, onSelected: (Int) -> Unit) {
  Row(Modifier.background(WeComposeTheme.colors.bottomBar).navigationBarsPadding()) {
    TabItem(if (selected == 0) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined, "聊天",
      if (selected == 0) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
      Modifier.weight(1f).clickable { onSelected(0) })
    TabItem(if (selected == 1) R.drawable.ic_contacts_filled else R.drawable.ic_contacts_outlined, "通讯录",
      if (selected == 1) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
      Modifier.weight(1f).clickable { onSelected(1) })
    TabItem(if (selected == 2) R.drawable.ic_discovery_filled else R.drawable.ic_discovery_outlined, "发现",
      if (selected == 2) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
      Modifier.weight(1f).clickable { onSelected(2) })
    TabItem(if (selected == 3) R.drawable.ic_me_filled else R.drawable.ic_me_outlined, "我",
      if (selected == 3) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
      Modifier.weight(1f).clickable { onSelected(3) })
  }
}

@Preview(showBackground = true)
@Composable
private fun WeNavigationBarPreview() {
  var selectedTab by remember { mutableIntStateOf(0) }
  WeNavigationBar(selectedTab) { selectedTab = it }
}

@Preview(showBackground = true)
@Composable
private fun WeNavigationBarDarkPreview() {
  var selectedTab by remember { mutableIntStateOf(0) }
  WeComposeTheme(WeComposeTheme.Theme.Dark) {
    WeNavigationBar(selectedTab) { selectedTab = it }
  }
}

@Preview(showBackground = true)
@Composable
private fun WeNavigationBarNewYearPreview() {
  var selectedTab by remember { mutableIntStateOf(0) }
  WeComposeTheme(WeComposeTheme.Theme.NewYear) {
    WeNavigationBar(selectedTab) { selectedTab = it }
  }
}

@Composable
private fun TabItem(@DrawableRes iconId: Int, title: String, tint: Color, modifier: Modifier = Modifier) {
  Column(modifier.padding(top = 10.dp, bottom = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally) {
    Icon(painterResource(iconId), title, Modifier.size(24.dp), tint = tint)
    Text(title, fontSize = 11.sp, color = tint)
  }
}

@Preview(showBackground = true)
@Composable
private fun TabItemPreview() {
  TabItem(R.drawable.ic_chat_outlined, "聊天", WeComposeTheme.colors.icon)
}