package hu.bme.aut.android.chat_app.Model

data class Conversation (
    var name: String,
    val type: String,
    val messages: MutableList<Message>
        )