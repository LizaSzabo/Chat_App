package hu.bme.aut.android.chatApp.domain

import hu.bme.aut.android.chatApp.ChatApplication
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.Network.addNewRegisteredUser
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