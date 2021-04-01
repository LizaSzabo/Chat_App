package hu.bme.aut.android.chat_app.ui.ChangePass

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.ui.ChangePass.Initial

import javax.inject.Inject

class ChangePassViewModel @Inject constructor(
    private val changePassPresenter: ChangePassPresenter
) : RainbowCakeViewModel<ChangePassViewState>(Initial){}
