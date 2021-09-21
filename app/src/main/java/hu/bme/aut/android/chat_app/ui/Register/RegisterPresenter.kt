package hu.bme.aut.android.chat_app.ui.Register

import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.addNewRegisteredUser
import javax.inject.Inject

class RegisterPresenter @Inject constructor(){
    fun getLoginResult()  {

    }

    fun saveRegisteredUser(user: User){
        addNewRegisteredUser(user.userName, user.password, user.profilePicture)
    }
}