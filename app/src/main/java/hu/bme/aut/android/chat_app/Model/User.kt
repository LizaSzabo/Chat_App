package hu.bme.aut.android.chat_app.Model

import android.graphics.Bitmap
import android.net.Uri

data class User (
    var userName: String,
    var password: String,
    var profilePicture: Bitmap,
    var conversations: MutableList<Conversation>?
        )