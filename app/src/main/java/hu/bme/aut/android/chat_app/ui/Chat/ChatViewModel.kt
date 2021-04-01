package hu.bme.aut.android.chat_app.ui.Chat

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.ui.Login.Initial
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val chatPresenter: ChatPresenter
) : RainbowCakeViewModel<ChatViewState>(hu.bme.aut.android.chat_app.ui.Chat.Initial) {

}