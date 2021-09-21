package hu.bme.aut.android.chat_app.domain

import hu.bme.aut.android.chat_app.ChatApplication
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.addNewRegisteredUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterInteractor @Inject constructor() {

    fun saveRegisteredUser(user : User) {
        addNewRegisteredUser(user.userName, user.password, user.profilePicture)
        ChatApplication.userid++
        ChatApplication.usersList.add(user)
    }
}