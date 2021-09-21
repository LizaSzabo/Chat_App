package hu.bme.aut.android.chat_app.ui.Register

import android.graphics.Bitmap
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.ChatApplication.Companion.userid
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.User
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(Initial) {

    fun saveRegisteredUser(profilePicture: Bitmap, userName: String, userPassword: String) = execute {
        val user = User(userName, userPassword, profilePicture, conversations = mutableListOf())
        registerPresenter.saveRegisteredUser(user)
    }

}
