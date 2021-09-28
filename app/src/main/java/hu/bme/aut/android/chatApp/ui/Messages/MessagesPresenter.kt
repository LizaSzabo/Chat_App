package hu.bme.aut.android.chatApp.ui.Messages

import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.Network.UpdateUser
import javax.inject.Inject

class MessagesPresenter @Inject constructor(){

    fun updateUser(user: User){
        UpdateUser(user)
    }
}