package hu.bme.aut.android.chatApp.ui.Register

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.domain.UserInteractor
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val userInteractor: UserInteractor,
){

    suspend fun saveRegisteredUser(user: User) : Boolean = withIOContext {
        userInteractor.saveRegisteredUser(user)
    }

    suspend fun existsUser(userName: String) : Boolean = withIOContext {
        userInteractor.existsUserName(userName)
    }
}