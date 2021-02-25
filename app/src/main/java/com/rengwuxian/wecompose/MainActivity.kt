package com.rengwuxian.wecompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.rengwuxian.wecompose.ui.Home
import com.rengwuxian.wecompose.ui.theme.WeTheme
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    val viewModel: WeViewModel by viewModels()
    setContent {
      WeTheme(theme = viewModel.theme) {
        Home()
      }
    }
  }

  override fun onBackPressed() {
    val viewModel: WeViewModel by viewModels()
    if (viewModel.openModule != null) {
      viewModel.endChat()
    } else {
      super.onBackPressed()
    }
  }
}

fun Modifier.percentOffsetX(percent: Float) = this.layout { measurable, constraints ->
  val placeable = measurable.measure(constraints)
  layout(placeable.width, placeable.height) {
    placeable.placeRelative(IntOffset((placeable.width * percent).roundToInt(), 0))
  }
}

/**
 * 增加未读小红点
 */
fun Modifier.unread(read: Boolean, badgeColor: Color) = this
  .drawWithContent {
    drawContent()
    if (!read) {
      drawCircle(
        color = badgeColor,
        radius = 5.dp.toPx(),
        center = Offset(size.width - 1.dp.toPx(), 1.dp.toPx()),
      )
    }
  }