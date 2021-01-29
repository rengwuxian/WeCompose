package com.rengwuxian.wecompose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.rengwuxian.wecompose.R

@Composable
fun WeTopBar(title: String, onBack: (() -> Unit)? = null) {
  Box {
    TopAppBar(
      Modifier.preferredHeight(48.dp),
      backgroundColor = MaterialTheme.colors.surface) {
      if (onBack != null) {
        Icon(
          vectorResource(R.drawable.ic_back),
          Modifier
            .clickable(onClick = onBack)
            .align(Alignment.CenterVertically)
            .size(36.dp)
            .padding(8.dp)
        )
      }
    }
    Text(title, Modifier.align(Alignment.Center))
  }
}