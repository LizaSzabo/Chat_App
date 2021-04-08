package hu.bme.aut.android.chat_app.Model

import java.util.*

data class Message (
    var sender: String,
    val receivers: String,
    val content: String,
    val date: String
        )