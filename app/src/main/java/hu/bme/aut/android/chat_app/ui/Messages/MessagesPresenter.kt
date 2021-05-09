package hu.bme.aut.android.chat_app.ui.Messages

import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.UpdateUser
import javax.inject.Inject

class MessagesPresenter @Inject constructor(){

    fun updateUser(user: User){
        UpdateUser(user)
    }
}