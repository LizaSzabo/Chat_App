package hu.bme.aut.android.chatApp.ui.Chat

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.Message
import hu.bme.aut.android.chatApp.domain.MessageInteractor
import javax.inject.Inject

class ChatPresenter @Inject constructor(
    private val messageInteractor : MessageInteractor
){
    suspend fun loadAllMessages(currentConversationId: String) : List<Message> = withIOContext {
        messageInteractor.loadAllMessages(currentConversationId)
    }

    suspend fun addMessage(message : Message, currentConversationId: String) : Boolean = withIOContext {
        messageInteractor.addMessage(message, currentConversationId)
    }
}