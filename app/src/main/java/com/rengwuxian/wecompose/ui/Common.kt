package com.rengwuxian.wecompose.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rengwuxian.wecompose.R
import com.rengwuxian.wecompose.WeViewModel
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

@Composable
fun WeTopBar(title: String, onBack: (() -> Unit)? = null) {
  val viewModel: WeViewModel = viewModel(LocalActivity.current as ViewModelStoreOwner)
  Box(Modifier.background(WeComposeTheme.colors.background).fillMaxWidth()) {
    Row(Modifier.height(40.dp)
    ) {
      if (onBack != null) {
        Icon(
          painterResource(R.drawable.ic_back),
          null,
          Modifier
            .clickable(onClick = onBack)
            .align(Alignment.CenterVertically)
            .size(36.dp)
            .padding(8.dp),
          tint = WeComposeTheme.colors.icon
        )
      }
      Spacer(Modifier.weight(1f))
      Icon(
        painterResource(R.drawable.ic_palette),
        "切换主题",
        Modifier
          .clickable { viewModel.switchTheme() }
          .align(Alignment.CenterVertically)
          .size(36.dp)
          .padding(8.dp),
        tint = WeComposeTheme.colors.icon
      )
    }
    Text(title, Modifier.align(Alignment.Center), color = WeComposeTheme.colors.textPrimary)
  }
}