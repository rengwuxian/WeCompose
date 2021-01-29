package com.rengwuxian.wecompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import com.rengwuxian.wecompose.data.Chat
import com.rengwuxian.wecompose.data.Msg
import com.rengwuxian.wecompose.data.User
import com.rengwuxian.wecompose.ui.WeBottomBar
import com.rengwuxian.wecompose.ui.WeChat
import com.rengwuxian.wecompose.ui.WeHomeTopBar
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WeComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          Box {
            Scaffold(topBar = {
              WeHomeTopBar()
            }, bottomBar = {
              WeBottomBar()
            }) {
              val chats = listOf<Chat>(
                Chat(
                  friend = User("gaolaoshi", "高老师", R.drawable.avatar_1),
                  listOf(Msg(User("gaolaoshi", "高老师", R.drawable.avatar_1), "哈哈"))
                ),
                Chat(
                  friend = User("diuwuxian", "丢物线", R.drawable.avatar_2),
                  listOf(Msg(User("gaolaoshi", "丢物线", R.drawable.avatar_2), "笑个屁"))
                ),
              )
              ChatList(chats = chats)
            }
            val viewModel: WeViewModel = viewModel()
            val chatPercentOffsetX by animateAsState(
              if (viewModel.chatting) {
                0f
              } else {
                1f
              }
            )
            WeChat(
              Modifier.percentOffsetX(chatPercentOffsetX),
              chat = viewModel.currentChat
            ) {
              viewModel.endChat()
            }
          }
        }
      }
    }
  }
}

fun Modifier.percentOffsetX(percent: Float) = this.layout { measurable, constraints ->
  val placeable = measurable.measure(constraints)
  layout(placeable.width, placeable.height) {
    placeable.placeRelative(IntOffset((placeable.width * percent).roundToInt(), 0))
  }
}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

@Composable
fun ChatListItem(
  avatar: ImageVector,
  chat: Chat,
  modifier: Modifier = Modifier,
  viewModel: WeViewModel = viewModel()
) {
  Row(
    Modifier
      .fillMaxWidth()
      .clickable(onClick = {
        viewModel.startChat(chat)
      })
  ) {
    Image(
      avatar, Modifier
        .padding(12.dp, 8.dp, 8.dp, 8.dp)
        .size(48.dp)
        .unread(chat.msgs.last().read)
        .clip(RoundedCornerShape(4.dp))
    )
    Column(
      Modifier
        .weight(1f)
        .align(Alignment.CenterVertically)
    ) {
      Text(chat.friend.name, fontSize = 17.sp)
      Providers(AmbientContentAlpha provides ContentAlpha.disabled) {
        Text(chat.msgs.last().text, fontSize = 14.sp)
      }
    }
    Providers(AmbientContentAlpha provides ContentAlpha.disabled) {
      Text(
        "13:48",
        Modifier.padding(8.dp, 8.dp, 12.dp, 8.dp),
        fontSize = 11.sp
      )
    }
  }
}

/**
 * 增加未读小红点
 */
fun Modifier.unread(read: Boolean) = this
  .drawWithContent {
    drawContent()
    if (!read) {
      drawIntoCanvas { canvas ->
        val paint = Paint().apply {
          color = Color.Red
        }
        canvas.drawCircle(Offset(size.width - 1.dp.toPx(), 1.dp.toPx()), 5.dp.toPx(), paint)
      }
    }
  }

@Composable
fun ChatList(chats: List<Chat>) {
  LazyColumn(content = {
    itemsIndexed(chats) { index, chat ->
      ChatListItem(avatar = vectorResource(id = R.drawable.avatar_1), chat)
      if (index < chats.size - 1) {
        Divider(startIndent = 76.dp)
      }
    }
  })
}

@Preview(showBackground = true)
@Composable
fun ChatListItemPreview() {
  WeComposeTheme {
    Box {
      ChatListItem(
        vectorResource(id = R.drawable.avatar_1),
        Chat(
          friend = User("gaolaoshi", "高老师", R.drawable.avatar_1),
          listOf(Msg(User("gaolaoshi", "高老师", R.drawable.avatar_1), "哈哈"))
        ),
      )
    }

  }
}

@Preview(showBackground = true)
@Composable
fun ChatListPreview() {
  val chats = listOf<Chat>(
    Chat(
      friend = User("gaolaoshi", "高老师", R.drawable.avatar_1),
      listOf(Msg(User("gaolaoshi", "高老师", R.drawable.avatar_1), "哈哈"))
    ),
    Chat(
      friend = User("diuwuxian", "丢物线", R.drawable.avatar_2),
      listOf(Msg(User("gaolaoshi", "丢物线", R.drawable.avatar_2), "笑个屁"))
    ),
  )
  ChatList(chats = chats)
}