package hu.bme.aut.android.chatApp.ui.ViewUsers

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class ViewUsersViewModel @Inject constructor(
    private val viewUsersPresenter: ViewUsersPresenter
) : RainbowCakeViewModel<ViewUsersViewState>(Initial){

}