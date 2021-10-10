package hu.bme.aut.android.chatApp.domain

import hu.bme.aut.android.chatApp.ChatApplication.Companion.Messages
import hu.bme.aut.android.chatApp.Model.Message
import javax.inject.Inject

class MessageInteractor @Inject constructor() {

    fun loadAllMessages() : List<Message> {
        return Messages
    }

    fun addMessage(message : Message) : Boolean{
        Messages.add(message)
        return true
    }
}