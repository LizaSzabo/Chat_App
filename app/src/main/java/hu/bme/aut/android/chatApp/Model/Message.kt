package hu.bme.aut.android.chatApp.Model


data class Message(
    val id: String,
    val senderId: String,
    val content: String,
    val date: String
)