package hu.bme.aut.android.chat_app.Model

data class User (
    var userName: String,
    var password: String,
    var profilePicture: Int,
    var conversations: MutableList<Conversation>?
        )