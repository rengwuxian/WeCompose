package com.rengwuxian.wecompose.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rengwuxian.wecompose.R
import com.rengwuxian.wecompose.WeViewModel
import com.rengwuxian.wecompose.data.Msg
import com.rengwuxian.wecompose.data.User
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
data class ChatDetails(val userId: String)

@Composable
fun ChatDetailsPage(viewModel: WeViewModel, userId: String) {
  val chat = viewModel.chats.find { it.friend.id == userId }!!
  var shakingTime by remember { mutableIntStateOf(0) }
  Column(
    Modifier
      .background(WeComposeTheme.colors.background)
      .fillMaxSize()
      .statusBarsPadding()
  ) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    WeTopBar(chat.friend.name) { backDispatcher?.onBackPressed() }
    Box(
      Modifier
        .background(WeComposeTheme.colors.chatPage)
        .weight(1f)
    ) {
      Box(
        Modifier
          .alpha(WeComposeTheme.colors.chatPageBgAlpha)
          .fillMaxSize()
      ) {
        Image(
          painterResource(R.drawable.ic_bg_newyear_left), null,
          Modifier
            .align(Alignment.CenterStart)
            .padding(bottom = 100.dp)
        )
        Image(
          painterResource(R.drawable.ic_bg_newyear_top), null,
          Modifier
            .align(Alignment.TopEnd)
            .padding(horizontal = 24.dp)
        )
        Image(
          painterResource(R.drawable.ic_bg_newyear_right), null,
          Modifier
            .align(Alignment.BottomEnd)
            .padding(vertical = 200.dp)
        )
      }
      val shakingOffset = remember { Animatable(0f) }
      LaunchedEffect(shakingTime) {
        if (shakingTime != 0) {
          shakingOffset.animateTo(
            0f,
            animationSpec = spring(0.3f, 600f),
            initialVelocity = -2000f
          ) { }
        }
      }
      LazyColumn(
        Modifier
          .fillMaxSize()
          .offset(shakingOffset.value.dp, shakingOffset.value.dp)
      ) {
        items(chat.msgs.size) { index ->
          val msg = chat.msgs[index]
          MessageItem(msg, shakingTime, chat.msgs.size - index - 1)
        }
      }
    }
    ChatBottomBar(onBombClicked = {
      viewModel.boom(chat)
      shakingTime++
    })
  }
}

@Composable
fun MessageItem(msg: Msg, shakingTime: Int, shakingLevel: Int) {
  val shakingAngleBubble = remember { Animatable(0f) }
  LaunchedEffect(key1 = shakingTime, block = {
    if (shakingTime != 0) {
      delay(shakingLevel.toLong() * 30)
      shakingAngleBubble.animateTo(
        0f,
        animationSpec = spring(0.4f, 500f),
        initialVelocity = 1200f / (1 + shakingLevel * 0.4f)
      )
    }
  })
  if (msg.from == User.Me) {
    Row(
      Modifier
        .fillMaxWidth()
        .padding(8.dp),
      horizontalArrangement = Arrangement.End
    ) {
      val bubbleColor = WeComposeTheme.colors.bubbleMe
      Text(
        "${msg.text}",
        Modifier
          .graphicsLayer(
            rotationZ = shakingAngleBubble.value,
            transformOrigin = TransformOrigin(1f, 0f)
          )
          .drawBehind {
            val bubble = Path().apply {
              val rect = RoundRect(
                10.dp.toPx(),
                0f,
                size.width - 10.dp.toPx(),
                size.height,
                4.dp.toPx(),
                4.dp.toPx()
              )
              addRoundRect(rect)
              moveTo(size.width - 10.dp.toPx(), 15.dp.toPx())
              lineTo(size.width - 5.dp.toPx(), 20.dp.toPx())
              lineTo(size.width - 10.dp.toPx(), 25.dp.toPx())
              close()
            }
            drawPath(bubble, bubbleColor)
          }
          .padding(20.dp, 10.dp),
        color = WeComposeTheme.colors.textPrimaryMe)
      Image(
        painterResource(msg.from.avatar),
        contentDescription = msg.from.name,
        Modifier
          .graphicsLayer(
            rotationZ = shakingAngleBubble.value * 0.6f,
            transformOrigin = TransformOrigin(1f, 0f)
          )
          .size(40.dp)
          .clip(RoundedCornerShape(4.dp))
      )
    }
  } else {
    Row(
      Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Image(
        painterResource(msg.from.avatar),
        contentDescription = msg.from.name,
        Modifier
          .graphicsLayer(
            rotationZ = -shakingAngleBubble.value * 0.6f,
            transformOrigin = TransformOrigin(0f, 0f)
          )
          .size(40.dp)
          .clip(RoundedCornerShape(4.dp))
      )
      val bubbleColor = WeComposeTheme.colors.bubbleOthers
      Text(
        "${msg.text}",
        Modifier
          .graphicsLayer(
            rotationZ = -shakingAngleBubble.value,
            transformOrigin = TransformOrigin(0f, 0f)
          )
          .drawBehind {
            val bubble = Path().apply {
              val rect = RoundRect(
                10.dp.toPx(),
                0f,
                size.width - 10.dp.toPx(),
                size.height,
                4.dp.toPx(),
                4.dp.toPx()
              )
              addRoundRect(rect)
              moveTo(10.dp.toPx(), 15.dp.toPx())
              lineTo(5.dp.toPx(), 20.dp.toPx())
              lineTo(10.dp.toPx(), 25.dp.toPx())
              close()
            }
            drawPath(bubble, bubbleColor)
          }
          .padding(20.dp, 10.dp),
        color = WeComposeTheme.colors.textPrimary)
    }
  }
}

@Composable
fun ChatBottomBar(onBombClicked: () -> Unit) {
  var editingText by remember { mutableStateOf("") }
  Row(
    Modifier
      .fillMaxWidth()
      .background(WeComposeTheme.colors.bottomBar)
      .padding(4.dp, 0.dp)
      .navigationBarsPadding()
  ) {
    Icon(
      painterResource(R.drawable.ic_voice),
      contentDescription = null,
      Modifier
        .align(Alignment.CenterVertically)
        .padding(4.dp)
        .size(28.dp),
      tint = WeComposeTheme.colors.icon
    )
    BasicTextField(
      editingText, { editingText = it },
      Modifier
        .weight(1f)
        .padding(4.dp, 8.dp)
        .height(40.dp)
        .clip(MaterialTheme.shapes.small)
        .background(WeComposeTheme.colors.textFieldBackground)
        .padding(start = 8.dp, top = 10.dp, end = 8.dp),
      cursorBrush = SolidColor(WeComposeTheme.colors.textPrimary)
    )
    Text(
      "\uD83D\uDCA3",
      Modifier
        .clickable(onClick = onBombClicked)
        .padding(4.dp)
        .align(Alignment.CenterVertically),
      fontSize = 24.sp
    )
    Icon(
      painterResource(R.drawable.ic_add),
      contentDescription = null,
      Modifier
        .align(Alignment.CenterVertically)
        .padding(4.dp)
        .size(28.dp),
      tint = WeComposeTheme.colors.icon
    )
  }
}