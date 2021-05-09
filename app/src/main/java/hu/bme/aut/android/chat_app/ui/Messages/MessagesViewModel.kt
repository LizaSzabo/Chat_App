package hu.bme.aut.android.chat_app.ui.Messages

import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chat_app.ui.AddConversationDialog
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.UpdateUser
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