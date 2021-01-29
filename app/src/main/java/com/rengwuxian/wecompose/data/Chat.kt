package com.rengwuxian.wecompose.data

data class Chat (var friend: User, var msgs: List<Msg>) {
}

data class Msg(val from: User, val text: String, var read: Boolean = false) {
}