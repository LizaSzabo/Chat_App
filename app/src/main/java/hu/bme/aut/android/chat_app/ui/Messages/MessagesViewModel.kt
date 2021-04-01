package hu.bme.aut.android.chat_app.ui.Messages

import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class MessagesViewModel@Inject constructor(
    private val messagesPresenter: MessagesPresenter): RainbowCakeViewModel<MessagesViewState>(Initial){

    fun openChatActivity(navController: NavController) {
        val action = MessagesFragmentDirections.actionMessagesFragmentToChatFragment()
        navController.navigate(action)
    }

     fun openEditProfileActivity(navController: NavController) {
        val action = MessagesFragmentDirections.actionMessagesFragmentToEditProfileFragment()
        navController.navigate(action)
    }
}