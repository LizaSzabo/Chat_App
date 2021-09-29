package hu.bme.aut.android.chatApp.ui.addUser

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class AddUserViewModel @Inject constructor(
    private val addUserPresenter: AddUserPresenter
) : RainbowCakeViewModel<AddUserViewState>(Initial) {

}