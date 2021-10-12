package hu.bme.aut.android.chatApp.ui.addConversation

import android.graphics.Bitmap
import android.util.Log
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import java.util.*
import javax.inject.Inject

class AddConversationViewModel @Inject constructor(
    private val addConversationPresenter: AddConversationPresenter
) : RainbowCakeViewModel<AddConversationViewState>(Initial) {
    private var exists: Boolean = false
    private var add: Boolean = false

    fun existsConversation(conversationCode: String) = execute {
        exists = addConversationPresenter.existsConversation(conversationCode)
        Log.i("exists",  exists.toString())
    }

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
            if(viewState is ConversationAddSuccess) postEvent(ConversationAdded)
    }

    object ConversationAdded : OneShotEvent
}
