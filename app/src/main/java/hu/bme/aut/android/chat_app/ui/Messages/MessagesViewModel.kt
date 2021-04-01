package hu.bme.aut.android.chat_app.ui.Messages

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class MessagesViewModel@Inject constructor(
    private val messagesPresenter: MessagesPresenter): RainbowCakeViewModel<MessagesViewState>(Initial){
}