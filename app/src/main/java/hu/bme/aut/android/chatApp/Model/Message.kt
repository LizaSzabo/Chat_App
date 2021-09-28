package hu.bme.aut.android.chatApp.Model


data class Message(
    var sender: String,
    val receivers: String,
    val content: String,
    val date: String
        )