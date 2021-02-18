package com.rengwuxian.wecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rengwuxian.wecompose.R
import com.rengwuxian.wecompose.WeViewModel
import com.rengwuxian.wecompose.data.User
import com.rengwuxian.wecompose.ui.theme.WeTheme

@Composable
fun ContactListTopBar() {
  WeTopBar(title = "通讯录")
}

@Preview(showBackground = true)
@Composable
fun ContactListTopBarPreview() {
  ContactListTopBar()
}

@Composable
fun ContactListItem(
  contact: User,
  modifier: Modifier = Modifier,
) {
  Row(
    Modifier
      .fillMaxWidth()
  ) {
    Image(
      painterResource(contact.avatar), "avatar", Modifier
        .padding(12.dp, 8.dp, 8.dp, 8.dp)
        .size(36.dp)
        .clip(RoundedCornerShape(4.dp))
    )
    Text(
      contact.name,
      Modifier
        .weight(1f)
        .align(Alignment.CenterVertically),
      fontSize = 17.sp,
      color = WeTheme.colors.textPrimary
    )
  }
}

@Composable
fun ContactList(viewModel: WeViewModel = viewModel()) {
  Column(Modifier.fillMaxSize()) {
    ContactListTopBar()
    Box(
      Modifier
        .background(WeTheme.colors.background)
        .fillMaxSize()
    ) {
      ContactList(viewModel.contacts)
    }
  }
}

@Composable
fun ContactList(contacts: List<User>) {
  LazyColumn(
    Modifier
      .background(WeTheme.colors.listItem)
      .fillMaxWidth()
  ) {
    val buttons = listOf(
      User("contact_add", "新的朋友", R.drawable.ic_contact_add),
      User("contact_chat", "仅聊天", R.drawable.ic_contact_chat),
      User("contact_group", "群聊", R.drawable.ic_contact_group),
      User("contact_tag", "标签", R.drawable.ic_contact_tag),
      User("contact_official", "公众号", R.drawable.ic_contact_official),
    )
    itemsIndexed(buttons) { index, contact ->
      ContactListItem(contact)
      if (index < buttons.size - 1) {
        Divider(startIndent = 56.dp, color = WeTheme.colors.chatListDivider, thickness = 0.8f.dp)
      }
    }
    item {
      Text(
        "朋友",
        Modifier
          .background(WeTheme.colors.background)
          .fillMaxWidth()
          .padding(12.dp, 8.dp),
        fontSize = 14.sp,
        color = WeTheme.colors.onBackground
      )
    }
    itemsIndexed(contacts) { index, contact ->
      ContactListItem(contact)
      if (index < contacts.size - 1) {
        Divider(startIndent = 56.dp, color = WeTheme.colors.chatListDivider, thickness = 0.8f.dp)
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ContactListItemPreview() {
  WeTheme {
    Box {
      ContactListItem(
        User("gaolaoshi", "高老师", R.drawable.avatar_1)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ContactListPreview() {
  val contacts = listOf<User>(
    User("gaolaoshi", "高老师", R.drawable.avatar_1),
    User("diuwuxian", "丢物线", R.drawable.avatar_2),
  )
  ContactList(contacts)
}