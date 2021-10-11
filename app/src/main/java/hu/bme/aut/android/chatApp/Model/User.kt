package hu.bme.aut.android.chatApp.Model

import android.graphics.Bitmap

data class User(
    var userName: String,
    var password: String,
    var profilePicture: Bitmap,
    var conversationsId: MutableList<String>
        )