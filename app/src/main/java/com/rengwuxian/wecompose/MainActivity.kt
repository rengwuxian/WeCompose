package com.rengwuxian.wecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import com.rengwuxian.wecompose.ui.ChatPage
import com.rengwuxian.wecompose.ui.Home
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

class MainActivity : ComponentActivity() {
  private val viewModel: WeViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WeComposeTheme(viewModel.theme) {
        Box {
          Home(viewModel)
          ChatPage()
        }
      }
    }
  }

  override fun onBackPressed() {
    if (!viewModel.endChat()) {
      super.onBackPressed()
    }
  }
}

