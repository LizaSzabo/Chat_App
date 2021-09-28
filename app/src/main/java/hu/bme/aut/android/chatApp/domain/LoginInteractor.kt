package hu.bme.aut.android.chatApp.domain

import hu.bme.aut.android.chatApp.ChatApplication
import hu.bme.aut.android.chatApp.Model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginInteractor @Inject constructor() {

    fun getUsers() : List<User>{
        return ChatApplication.usersList
    }
}