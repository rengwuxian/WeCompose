package com.rengwuxian.wecompose.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.packFloats
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rengwuxian.wecompose.R
import com.rengwuxian.wecompose.WeViewModel
import com.rengwuxian.wecompose.data.Chat
import com.rengwuxian.wecompose.data.Msg
import com.rengwuxian.wecompose.data.User
import com.rengwuxian.wecompose.ui.theme.WeTheme
import kotlinx.coroutines.delay

@Composable
fun ChatPage(
  modifier: Modifier = Modifier,
  viewModel: WeViewModel = viewModel(),
  chat: Chat?,
  onBack: (() -> Unit)
) {
  if (chat != null) {
    viewModel.read(chat)
    var shakingTime by remember { mutableStateOf(0) }
    Scaffold(
      modifier.background(WeTheme.colors.background),
      topBar = {
        WeTopBar(chat.friend.name, onBack)
      },
      bottomBar = {
        ChatBottomBar(onBombClicked = {
          viewModel.boom(chat)
          shakingTime += 1
        })
      }
    ) {
      val shakingOffset = remember { Animatable(0f) }
      LaunchedEffect(key1 = shakingTime, block = {
        if (shakingTime != 0) {
          shakingOffset.animateTo(
            0f,
            animationSpec = spring(0.3f, 600f),
            initialVelocity = -2000f
          )
        }
      })
      Spacer(
        Modifier
          .background(WeTheme.colors.chatPage)
          .fillMaxSize()
      )
      Box(
        Modifier
          .alpha(WeTheme.colors.chatPageBgAlpha)
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
  }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
  val chat = Chat(
    friend = User("gaolaoshi", "高老师", R.drawable.avatar_1),
    mutableListOf(Msg(User("gaolaoshi", "高老师", R.drawable.avatar_1), "哈哈"))
  )
  ChatPage(chat = chat, onBack = { /*TODO*/ })
}

@Composable
fun ChatBottomBar(onBombClicked: () -> Unit) {
  var editingText by remember { mutableStateOf("") }
  WeBottomBar {
    Icon(
      painterResource(R.drawable.ic_voice),
      contentDescription = null,
      Modifier
        .align(Alignment.CenterVertically)
        .padding(4.dp)
        .size(28.dp),
      tint = WeTheme.colors.icon
    )
    BasicTextField(
      editingText, onValueChange = { editingText = it },
      Modifier
        .weight(1f)
        .padding(4.dp, 8.dp)
        .preferredHeight(40.dp)
        .clip(MaterialTheme.shapes.small)
        .background(WeTheme.colors.textFieldBackground)
        .padding(start = 8.dp, top = 10.dp, end = 8.dp),
      cursorColor = WeTheme.colors.textPrimary
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
      tint = WeTheme.colors.icon
    )
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
      val bubbleColor = WeTheme.colors.bubbleMe
      Text(msg.text,
        Modifier
          .graphicsLayer(
            rotationZ = shakingAngleBubble.value,
            transformOrigin = TransformOrigin(packFloats(1f, 0f))
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
        color = WeTheme.colors.textPrimaryMe)
      Image(
        painterResource(msg.from.avatar),
        contentDescription = "Avatar Me",
        Modifier
          .graphicsLayer(
            rotationZ = shakingAngleBubble.value * 0.6f,
            transformOrigin = TransformOrigin(packFloats(1f, 0f))
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
        contentDescription = "Avatar Me",
        Modifier
          .graphicsLayer(
            rotationZ = -shakingAngleBubble.value * 0.6f,
            transformOrigin = TransformOrigin(packFloats(0f, 0f))
          )
          .size(40.dp)
          .clip(RoundedCornerShape(4.dp))
      )
      val bubbleColor = WeTheme.colors.bubbleOthers
      Text(msg.text,
        Modifier
          .graphicsLayer(
            rotationZ = -shakingAngleBubble.value,
            transformOrigin = TransformOrigin(packFloats(0f, 0f))
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
        color = WeTheme.colors.textPrimary)
    }
  }
}