package hu.bme.aut.android.chatApp.ui.addConversation

import android.graphics.Bitmap
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import javax.inject.Inject

class AddConversationViewModel @Inject constructor(
    private val addConversationPresenter: AddConversationPresenter
) : RainbowCakeViewModel<AddConversationViewState>(Initial) {
    private var add: Boolean = false

    fun addConversation(
        conversationId: String,
        conversationName: String,
        conversationType: String,
        conversationImage: Bitmap
    ) = execute {
        val conversation = Conversation(
            conversationId,
            conversationName,
            conversationType,
            mutableListOf(),
            mutableListOf(currentUser?.id!!),
            conversationImage,
            false
        )
        add = addConversationPresenter.addConversation(conversation)
        viewState = if (add) ConversationAddSuccess else ConversationAddError
        if (viewState is ConversationAddSuccess) postEvent(ConversationAdded)
        if (viewState is ConversationAddError) postEvent(ConversationAddedError)
    }

    object ConversationAdded : OneShotEvent
    object ConversationAddedError : OneShotEvent
}
