package hu.bme.aut.android.chatApp.ui.login

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.domain.LoginInteractor
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val loginInteractor: LoginInteractor
){
    suspend fun getUsers() = withIOContext {
        loginInteractor.getUsers()
    }
}