package hu.bme.aut.android.chatApp.ui.Messages

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chatApp.ui.addConversation.AddConversationDialog
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.ui.login.UsersInitError
import hu.bme.aut.android.chatApp.ui.login.UsersInitSuccess
import javax.inject.Inject

class MessagesViewModel@Inject constructor(
    private val messagesPresenter: MessagesPresenter): RainbowCakeViewModel<MessagesViewState>(Initial), AddConversationDialog.AddConversationListener{

    private lateinit var conversationsAdapter: ConversationsAdapter
    private var conversations : List<Conversation> = mutableListOf()

    fun init(searchText: String, adapter: ConversationsAdapter) = execute{
        conversations = messagesPresenter.getConversations()
        viewState = if (conversations.isNotEmpty()) ConversationLoadSuccess else ConversationLoadError
        conversationsAdapter = adapter
        conversationsAdapter.addAllConversations(conversations)
    }

    fun deleteConversation(position : Int, conversation: Conversation) = execute{
        val delete = messagesPresenter.deleteConversation(conversation)
        viewState = if(delete) ConversationDeleteSuccess else ConversationDeleteError
        conversationsAdapter.deleteConversation(position)
    }

    fun openAddConversationDialog(fm: FragmentManager, ca: ConversationsAdapter) {
        conversationsAdapter = ca
        val addconversationDialog = AddConversationDialog()
        addconversationDialog.listener = this
        addconversationDialog.show(fm, "")

    }

     fun openEditProfileActivity(navController: NavController) {
        val action = MessagesFragmentDirections.actionMessagesFragmentToEditProfileFragment()
        navController.navigate(action)
    }

    override fun onAddConversation(conversation: Conversation) {
        conversationsAdapter.addConversation(conversation)
    }

    fun updateUser(user: User){
       messagesPresenter.updateUser(user)
    }
}