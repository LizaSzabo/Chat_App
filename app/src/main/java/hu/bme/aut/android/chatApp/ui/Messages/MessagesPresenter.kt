package hu.bme.aut.android.chatApp.ui.Messages

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.Network.UpdateUser
import hu.bme.aut.android.chatApp.domain.ConversationInteractor
import javax.inject.Inject

class MessagesPresenter @Inject constructor(
   private val conversationInterector: ConversationInteractor
){

    fun updateUser(user: User){
        UpdateUser(user)
    }

    suspend fun getConversations(): List<Conversation> = withIOContext {
        conversationInterector.getConversations()
    }
}