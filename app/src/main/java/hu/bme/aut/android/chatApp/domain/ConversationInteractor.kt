package hu.bme.aut.android.chatApp.domain

import hu.bme.aut.android.chatApp.ChatApplication.Companion.Conversations
import hu.bme.aut.android.chatApp.Model.Conversation
import javax.inject.Inject

class ConversationInteractor @Inject constructor() {

   fun getConversations() : List<Conversation>{
        return Conversations
   }

   fun deleteConversation(conversation : Conversation) : Boolean{
       Conversations.remove(conversation)
       return true
   }
}