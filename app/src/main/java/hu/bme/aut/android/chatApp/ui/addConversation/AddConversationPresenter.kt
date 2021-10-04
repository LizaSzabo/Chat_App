package hu.bme.aut.android.chatApp.ui.addConversation

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.domain.ConversationInteractor
import javax.inject.Inject

class AddConversationPresenter @Inject constructor(
    private val conversationInteractor: ConversationInteractor
) {

    suspend fun existsConversation(conversationCode: String) : Boolean = withIOContext {
        true
    }

    suspend fun addConversation(conversation: Conversation) : Boolean = withIOContext {
        conversationInteractor.addConversation(conversation)
    }
}