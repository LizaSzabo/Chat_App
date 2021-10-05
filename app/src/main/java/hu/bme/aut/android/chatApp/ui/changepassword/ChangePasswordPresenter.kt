package hu.bme.aut.android.chatApp.ui.changepassword

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.domain.UserInteractor
import javax.inject.Inject

class ChangePasswordPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {

    suspend fun changeUserPassword(newPassword : String) : Boolean = withIOContext{
        userInteractor.changeUserPassword(newPassword)
    }
}