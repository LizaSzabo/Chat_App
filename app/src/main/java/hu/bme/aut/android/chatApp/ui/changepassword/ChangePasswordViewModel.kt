package hu.bme.aut.android.chatApp.ui.changepassword

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    private val changePasswordPresenter: ChangePasswordPresenter
) : RainbowCakeViewModel<ChangePasswordViewState>(Initial) {
    private var changed = false

    fun changeUserPassword(newPassword : String) = execute {
        changed = changePasswordPresenter.changeUserPassword(newPassword)
        if(changed){
            viewState = ChangeSuccess
        }
        else viewState = ChangeError
    }
}