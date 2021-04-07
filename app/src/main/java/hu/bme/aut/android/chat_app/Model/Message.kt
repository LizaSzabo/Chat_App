package hu.bme.aut.android.chat_app.Model

data class Message (
    var sender: String,
    val receivers: String,
    val content: String
        )