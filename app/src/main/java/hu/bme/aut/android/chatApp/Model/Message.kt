package hu.bme.aut.android.chatApp.Model


data class Message(
    val id: String,
    val sender: String,
    val receivers: String,
    val content: String,
    val date: String
        )