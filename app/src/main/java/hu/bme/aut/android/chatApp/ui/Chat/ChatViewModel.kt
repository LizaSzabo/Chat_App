package hu.bme.aut.android.chatApp.ui.Chat

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Adapter_Rv.ChatAdapter
import hu.bme.aut.android.chatApp.Model.Message
import java.util.*
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val chatPresenter: ChatPresenter
) : RainbowCakeViewModel<ChatViewState>(Initial) {
    private var messages: List<Message> = mutableListOf()
    private var added = false
    private lateinit var adapter: ChatAdapter

    fun loadAllMessages(chatAdapter: ChatAdapter, currentConversationId: String) = execute {
        adapter = chatAdapter
        messages = chatPresenter.loadAllMessages(currentConversationId)
        if (messages.isNotEmpty()) {
            viewState = MessageLoadSuccess
            adapter.addAllMessages(messages)
        }
    }

    fun addMessage(senderId: String, content: String, time: String, currentConversationId : String) = execute {
        val message = Message(UUID.randomUUID().toString(), senderId, content, time)
        added = chatPresenter.addMessage(message, currentConversationId)
        if (added) {
            viewState = MessageAddSuccess
            adapter.add(message)
        } else {
            viewState = MessageAddError
            postEvent(AddError)
        }
    }

    object AddError : OneShotEvent
}