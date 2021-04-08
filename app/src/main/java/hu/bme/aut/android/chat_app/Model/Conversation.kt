package hu.bme.aut.android.chat_app.Model

import android.net.Uri

data class Conversation (
    var name: String,
    val type: String,
    val messages: MutableList<Message>,
    var picture: Uri
        )