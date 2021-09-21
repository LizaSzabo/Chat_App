package hu.bme.aut.android.chat_app.ui.Register

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.addNewRegisteredUser
import hu.bme.aut.android.chat_app.domain.RegisterInteractor
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val registerInteractor: RegisterInteractor,
){
    fun getLoginResult()  {

    }

    suspend fun saveRegisteredUser(user: User) = withIOContext {
        registerInteractor.saveRegisteredUser(user)
    }
}