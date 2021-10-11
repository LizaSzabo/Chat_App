package hu.bme.aut.android.chatApp.domain

import android.graphics.Bitmap
import android.util.Log
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Conversations
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import javax.inject.Inject

class ConversationInteractor @Inject constructor() {

   fun getConversations() : List<Conversation>{
       val conversations = mutableListOf<Conversation>()
       for(conversation in Conversations)
           if(conversation.usersName.contains(currentUser?.userName))
               conversations.add(conversation)
        return conversations
      // return currentUser?.conversations!!
   }

   fun deleteConversation(conversation : Conversation) : Boolean{
      // Conversations.remove(conversation)
      // currentUser?.conversations?.remove(conversation)
       Conversations.remove(conversation)
       for(user in Users)
           if(conversation.usersName.contains(user.userName))
               user.conversationsId.remove(conversation.id)
       return true
   }

    fun addConversation(conversation: Conversation) : Boolean {
        Conversations.add(conversation)
        Log.i("conversationId", conversation.id)
        for(user in Users)
            if(conversation.usersName.contains(user.userName))
                user.conversationsId.add(conversation.id)
        return true
    }

    fun existsConversation(conversationCode: String) : Boolean {
        for(conversation in Conversations) //Conversations
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
        for(c in Conversations) //Conversations
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