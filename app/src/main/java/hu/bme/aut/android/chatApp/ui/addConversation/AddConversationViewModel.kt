package hu.bme.aut.android.chatApp.ui.addConversation

import android.graphics.Bitmap
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Model.Conversation
import java.util.*
import javax.inject.Inject

class AddConversationViewModel @Inject constructor(
    private val addConversationPresenter: AddConversationPresenter
) : RainbowCakeViewModel<AddConversationViewState>(Initial) {
    private var exists: Boolean = false
    private var add: Boolean = false

    private fun existsConversation(conversationCode: String) = execute {
        exists = addConversationPresenter.existsConversation(conversationCode)
    }

    fun addConversation(
        conversationName: String,
        conversationType: String,
        conversationImage: Bitmap,
        conversationCode: String
    ) = execute {
        existsConversation(conversationCode)
        if (exists) viewState = ConversationAddCancel
        else {
            val conversation = Conversation(
                UUID.randomUUID().toString(),
                conversationName,
                conversationType,
                mutableListOf(),
                conversationImage,
                false,
                conversationCode
            )
            add = addConversationPresenter.addConversation(conversation)
            viewState = if (add) ConversationAddSuccess else ConversationAddError
        }
    }
}
