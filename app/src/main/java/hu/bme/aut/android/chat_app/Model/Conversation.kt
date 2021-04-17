package hu.bme.aut.android.chat_app.Model

import android.graphics.Bitmap
import android.net.Uri

data class Conversation(
    val id: String,
    var name: String,
    val type: String,
    val messages: MutableList<Message>?,
    var picture: Bitmap,
    var favourite: Boolean
        )