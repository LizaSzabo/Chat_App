package hu.bme.aut.android.chatApp.ui.Messages

import android.graphics.Bitmap
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.domain.ConversationInteractor
import javax.inject.Inject

class MessagesPresenter @Inject constructor(
   private val conversationInterector: ConversationInteractor
){

    suspend fun getConversations(): List<Conversation> = withIOContext {
        conversationInterector.getConversations()
    }

    suspend fun deleteConversation(conversation : Conversation) : Boolean = withIOContext {
        conversationInterector.deleteConversation(conversation)
    }

    suspend fun updateConversationImage(conversation: Conversation, conversationPicture : Bitmap) : Boolean = withIOContext {
        conversationInterector.updateConversationImage(conversation, conversationPicture)
    }

    suspend fun updateConversationFavourite(conversation : Conversation) : Boolean = withIOContext{
        conversationInterector.updateConversationFavourite(conversation)
    }
}