package hu.bme.aut.android.chatApp.ui.Chat

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val chatPresenter: ChatPresenter
) : RainbowCakeViewModel<ChatViewState>(hu.bme.aut.android.chatApp.ui.Chat.Initial) {

}