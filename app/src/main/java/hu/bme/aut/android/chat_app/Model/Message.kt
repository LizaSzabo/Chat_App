package hu.bme.aut.android.chat_app.Model

data class Message (
    val sender: String,
    val receivers: String,
    val content: String
        )