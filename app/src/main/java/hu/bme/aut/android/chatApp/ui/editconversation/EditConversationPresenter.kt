package hu.bme.aut.android.chatApp.ui.editconversation

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.domain.ConversationInteractor
import javax.inject.Inject

class EditConversationPresenter @Inject constructor(
    private val conversationInteractor: ConversationInteractor
){

    suspend fun updateConversationName(conversation : Conversation, conversationNewName : String ) : Boolean = withIOContext {
        conversationInteractor.updateConversationName(conversation, conversationNewName)
    }
}