package hu.bme.aut.android.chatApp.ui.editusername

import android.util.Log
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.domain.UserInteractor
import javax.inject.Inject

class EditUserNamePresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun existsUserName(newUserName: String): Boolean = withIOContext {

        userInteractor.existsUserName(newUserName)
    }

    suspend fun updateUserName(newUserName: String) : Boolean = withIOContext {
        userInteractor.updateUserName(newUserName)
    }
}