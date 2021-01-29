package com.rengwuxian.wecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rengwuxian.wecompose.R
import com.rengwuxian.wecompose.ui.WeTopBar

@Preview(showBackground = true)
@Composable
fun WeHomeTopBarPreview() {
  WeHomeTopBar()
}

@Composable
fun WeHomeTopBar() {
  WeTopBar(title = "WeChat (1)")
}

@Composable
fun WeBottomBar() {
  Row {
    WeBottomItem(Modifier.weight(1f))
    WeBottomItem(Modifier.weight(1f))
    WeBottomItem(Modifier.weight(1f))
    WeBottomItem(Modifier.weight(1f))
  }
}

@Composable
fun WeBottomItem(modifier: Modifier = Modifier) {
  Column(modifier.padding(0.dp, 4.dp, 0.dp, 4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
    Image(vectorResource(R.drawable.avatar_1), Modifier.size(24.dp))
    Text("聊天")
  }
}

@Preview(showBackground = true)
@Composable
fun WeBottomPreview() {
  WeBottomBar()
}