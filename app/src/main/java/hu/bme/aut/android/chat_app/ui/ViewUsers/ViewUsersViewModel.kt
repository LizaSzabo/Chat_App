package hu.bme.aut.android.chat_app.ui.ViewUsers

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.ui.ViewUsers.Initial
import hu.bme.aut.android.chat_app.ui.Register.RegisterPresenter
import hu.bme.aut.android.chat_app.ui.Register.RegisterViewState
import javax.inject.Inject

class ViewUsersViewModel @Inject constructor(
    private val viewUsersPresenter: ViewUsersPresenter
) : RainbowCakeViewModel<ViewUsersViewState>(Initial){

}