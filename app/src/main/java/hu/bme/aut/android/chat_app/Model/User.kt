package hu.bme.aut.android.chat_app.Model

import android.net.Uri

data class User (
    var userName: String,
    var password: String,
    var profilePicture: Uri,
    var conversations: MutableList<Conversation>?
        )