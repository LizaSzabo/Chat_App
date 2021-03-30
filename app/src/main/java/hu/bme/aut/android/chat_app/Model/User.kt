package hu.bme.aut.android.chat_app.Model

data class User (
    val userName: String,
    val password: String,
    var profilePicture: Int,
    val conversations: MutableList<Conversation>
        )