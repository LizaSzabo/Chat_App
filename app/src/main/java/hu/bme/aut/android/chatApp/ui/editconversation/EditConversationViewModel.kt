package hu.bme.aut.android.chatApp.ui.editconversation

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Model.Conversation
import javax.inject.Inject

class EditConversationViewModel @Inject constructor(
    private val editConversationPresenter: EditConversationPresenter
) : RainbowCakeViewModel<EditConversationViewState>(Initial) {
    private var updated = false

    fun updateConversationName(conversation: Conversation, conversationNewName: String) = execute {
        updated = editConversationPresenter.updateConversationName(conversation, conversationNewName)
        if (updated) {
            viewState = UpdateSucceeded
            postEvent(ConversationUpdated)
        } else
            viewState = UpdateError
    }

    object ConversationUpdated : OneShotEvent
}