package com.rengwuxian.wecompose.data

import androidx.annotation.DrawableRes
import com.rengwuxian.wecompose.R

data class User(
  val id: String,
  val name: String,
  @DrawableRes val avatar: Int
) {
  companion object {
    val Me: User = User("rengwuxian", "扔物线-朱凯", R.drawable.avatar_rengwuxian)
  }
}