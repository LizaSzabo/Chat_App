package hu.bme.aut.android.chatApp.ui.Register

import android.graphics.Bitmap
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Model.User
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(Initial) {
    private var exists = true
    private var registered = false

    fun saveRegisteredUser(profilePicture: Bitmap, userName: String, userPassword: String) = execute {
        exists = registerPresenter.existsUser(userName)
        if(exists){
            viewState = RegistrationCancelled
            postEvent(UserAlreadyExists)
        }
        else{
            val user = User(userName, userPassword, profilePicture, conversations = mutableListOf())
            registered = registerPresenter.saveRegisteredUser(user)
            if(registered) {
                viewState = RegistrationSuccess
                postEvent(RegistrationSucceeded)
            }
            else {
                viewState = RegistrationError
            }
        }
    }

    object UserAlreadyExists : OneShotEvent
    object RegistrationSucceeded : OneShotEvent
}
