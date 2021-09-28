package hu.bme.aut.android.chatApp.ui.Register

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.domain.RegisterInteractor
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val registerInteractor: RegisterInteractor,
){

    suspend fun saveRegisteredUser(user: User) = withIOContext {
        registerInteractor.saveRegisteredUser(user)
    }
}