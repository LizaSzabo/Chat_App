package hu.bme.aut.android.chatApp.ui.addUser

import android.util.Log
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Model.Conversation
import javax.inject.Inject

class AddUserViewModel @Inject constructor(
    private val addUserPresenter: AddUserPresenter
) : RainbowCakeViewModel<AddUserViewState>(Initial) {

    fun addUserToConversation(userName: String, conversation: Conversation) = execute {
        val alreadyExists = addUserPresenter.existsUserName(userName)
        Log.i("exists", alreadyExists.toString())
        if (!alreadyExists) {
            viewState = UserAddedCancel
            postEvent(AddUserCancel)
        } else {
            val alreadyAdded = addUserPresenter.isUserNameAdded(userName, conversation)
            if(alreadyAdded){
                viewState = UserAddedCancel
                postEvent(AddUserAlreadyAdded)
            }
            else {
                val added = addUserPresenter.addUserToConversation(userName, conversation)
                if (added) viewState = UserAddedSuccess else UserAddedError
            }
        }
    }

    object AddUserCancel : OneShotEvent
    object AddUserAlreadyAdded: OneShotEvent
}