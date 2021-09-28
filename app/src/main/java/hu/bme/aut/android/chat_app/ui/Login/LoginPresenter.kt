package hu.bme.aut.android.chat_app.ui.Login

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chat_app.domain.LoginInteractor
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val loginInteractor: LoginInteractor
){
    suspend fun getUsers() = withIOContext {
        loginInteractor.getUsers()
    }
}