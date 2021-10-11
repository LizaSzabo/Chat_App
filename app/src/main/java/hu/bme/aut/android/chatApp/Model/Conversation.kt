package hu.bme.aut.android.chatApp.Model

import android.graphics.Bitmap


data class Conversation(
    val id: String,
    var name: String,
    val type: String,
    val messagesId: MutableList<String>,
    val usersName : MutableList<String>,
    var picture: Bitmap,
    var favourite: Boolean,
    val code: String
        )