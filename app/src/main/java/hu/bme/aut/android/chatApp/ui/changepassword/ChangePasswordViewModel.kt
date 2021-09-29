package hu.bme.aut.android.chatApp.ui.changepassword

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    private val changePasswordPresenter: ChangePasswordPresenter
) : RainbowCakeViewModel<ChangePasswordViewState>(Initial) {

}