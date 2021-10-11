package hu.bme.aut.android.chatApp.domain

import android.util.Log
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Conversations
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Messages
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Message
import javax.inject.Inject

class MessageInteractor @Inject constructor() {

    fun loadAllMessages(conversationId : String) : List<Message> {
        val messages = mutableListOf<Message>()
        for(conversation in Conversations)
            if(conversation.id == conversationId)
                 for(message in Messages)
                     if(conversation.messagesId.contains(message.id))
                         messages.add(message)
       return  messages
    }

    fun addMessage(message : Message, conversationId : String) : Boolean{
        Messages.add(message)
        Log.i("szia", conversationId)
        for(conversation in Conversations)
            if(conversation.id == conversationId) {
                conversation.messagesId.add(message.id)

            }
        return true
    }
}