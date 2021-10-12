package hu.bme.aut.android.chatApp.ui.Messages

import android.graphics.Bitmap
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.ui.addConversation.AddConversationDialog
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val messagesPresenter: MessagesPresenter
) : RainbowCakeViewModel<MessagesViewState>(Initial), AddConversationDialog.AddConversationListener {

    private lateinit var conversationsAdapter: ConversationsAdapter
    private var conversations: List<Conversation> = mutableListOf()

    fun init(searchText: String, adapter: ConversationsAdapter) = execute {
        conversations = messagesPresenter.getConversations()
        viewState = ConversationLoadSuccess //ConversationLoadError
        conversationsAdapter = adapter
        conversationsAdapter.addAllConversations(conversations)
    }

    fun deleteConversation(position: Int, conversation: Conversation) = execute {
        val delete = messagesPresenter.deleteConversation(conversation)
        viewState = if (delete) ConversationDeleteSuccess else ConversationDeleteError
        conversationsAdapter.deleteConversation(position)
    }

    fun openAddConversationDialog(fm: FragmentManager, ca: ConversationsAdapter) {
        conversationsAdapter = ca
        val addconversationDialog = AddConversationDialog()
        addconversationDialog.listener = this
        if (viewState is ConversationLoadSuccess || viewState is ConversationDeleteSuccess) addconversationDialog.show(fm, "")

    }

    fun openEditProfileActivity(navController: NavController) {
        val action = MessagesFragmentDirections.actionMessagesFragmentToEditProfileFragment()
        navController.navigate(action)
    }

    override fun onAddConversation(conversation: Conversation) {
        conversationsAdapter.addConversation(conversation)
    }

    fun updateConversationImage(conversation: Conversation, conversationPicture: Bitmap, pos: Int) = execute {
        val updated = messagesPresenter.updateConversationImage(conversation, conversationPicture)
        if (updated) {
            val updatedConversation = conversation.copy(picture = conversationPicture)
            conversationsAdapter.updateConversationPicture(updatedConversation, pos)
            postEvent(UpdateConversationImageSuccess)
        } else {
            postEvent(UpdateConversationImageError)
        }
    }

    fun updateConversationFavourite(conversation: Conversation, favourite: Boolean) = execute {
        val updatedConversation = conversation.copy(favourite = favourite)
        val updated = messagesPresenter.updateConversationFavourite(updatedConversation)
        viewState = if (updated) ConversationFavouriteUpdateSuccess else ConversationFavouriteUpdateError
    }

    object UpdateConversationImageError : OneShotEvent
    object UpdateConversationImageSuccess : OneShotEvent
}