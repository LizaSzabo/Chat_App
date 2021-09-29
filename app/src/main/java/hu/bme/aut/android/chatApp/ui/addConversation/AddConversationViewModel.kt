package hu.bme.aut.android.chatApp.ui.addConversation

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class AddConversationViewModel @Inject constructor(
    private val addConversationPresenter: AddConversationPresenter
) : RainbowCakeViewModel<AddConversationViewState>(Initial) {

}