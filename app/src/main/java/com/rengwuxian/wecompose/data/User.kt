package com.rengwuxian.wecompose.data

import androidx.annotation.DrawableRes

data class User(
  val id: String,
  val name: String,
  @DrawableRes val avatar: Int
)