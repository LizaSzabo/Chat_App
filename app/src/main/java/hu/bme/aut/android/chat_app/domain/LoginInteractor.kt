package hu.bme.aut.android.chat_app.domain

import hu.bme.aut.android.chat_app.ChatApplication
import hu.bme.aut.android.chat_app.Model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginInteractor @Inject constructor() {

    fun getUsers() : List<User>{
        return ChatApplication.usersList
    }
}