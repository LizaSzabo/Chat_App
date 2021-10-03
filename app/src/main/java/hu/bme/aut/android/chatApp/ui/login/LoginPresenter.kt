package hu.bme.aut.android.chatApp.ui.login

import android.util.Log
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.domain.UserInteractor
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val userInteractor: UserInteractor
){
    suspend fun getUsers() : List<User> = withIOContext {
         userInteractor.getUsers()
    }
}