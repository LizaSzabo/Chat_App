package hu.bme.aut.android.chatApp.ui.Messages

import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chatApp.ui.addConversation.AddConversationDialog
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import javax.inject.Inject

class MessagesViewModel@Inject constructor(
    private val messagesPresenter: MessagesPresenter): RainbowCakeViewModel<MessagesViewState>(Initial), AddConversationDialog.AddConversationListener{

    private lateinit var conversationsAdapter: ConversationsAdapter

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