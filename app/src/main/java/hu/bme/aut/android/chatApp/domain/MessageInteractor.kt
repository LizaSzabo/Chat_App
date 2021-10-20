package hu.bme.aut.android.chatApp.domain

import hu.bme.aut.android.chatApp.ChatApplication.Companion.Conversations
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Messages
import hu.bme.aut.android.chatApp.Model.Message
import hu.bme.aut.android.chatApp.Network.addMessageToConversation
import hu.bme.aut.android.chatApp.Network.getAllMessages
import hu.bme.aut.android.chatApp.Network.saveMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class MessageInteractor @Inject constructor() {

    fun loadAllMessages(conversationId: String): List<Message> {
        val messages = mutableListOf<Message>()

        for (conversation in Conversations)
            if (conversation.id == conversationId){
                for (message in Messages)
                    if (conversation.messagesId.contains(message.id))
                        messages.add(message)
            }

        return messages
    }


    fun addMessage(message: Message, conversationId: String): Boolean {
        saveMessage(message)
        Messages.add(message)

        for (conversation in Conversations)
            if (conversation.id == conversationId) {
                conversation.messagesId.add(message.id)
                addMessageToConversation(conversation, conversation.messagesId)
            }
        return true
    }
}