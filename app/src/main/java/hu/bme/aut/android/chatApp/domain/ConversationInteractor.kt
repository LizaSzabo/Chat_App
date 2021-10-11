package hu.bme.aut.android.chatApp.domain

import android.graphics.Bitmap
import android.util.Log
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Conversations
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
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

    fun addConversation(conversation: Conversation) : Boolean {
        Conversations.add(conversation)
        return true
    }

    fun existsConversation(conversationCode: String) : Boolean {
        for(conversation in Conversations)
            if(conversation.code == conversationCode){
                Log.i("exists", "true")
                return true
            }
        Log.i("exists", "false")
        return false
    }

    fun updateConversationName(conversation: Conversation, conversationNewName : String) : Boolean{
        for(c in Conversations)
            if(c.code == conversation.code){
                c.name = conversationNewName
                return true
            }
        return false
    }

    fun updateConversationImage(conversation : Conversation, conversationPicture : Bitmap) : Boolean{
        for(c in Conversations)
            if(c.code == conversation.code){
                c.picture = conversationPicture
                currentConversation?.picture = conversationPicture
                return true
            }
        return false
    }

    fun updateConversationFavourite(conversation : Conversation) : Boolean {
        for(c in Conversations)
            if(c.code == conversation.code){
                c.favourite = conversation.favourite
                currentConversation?.favourite = conversation.favourite
                return true
            }
        return false
    }
}