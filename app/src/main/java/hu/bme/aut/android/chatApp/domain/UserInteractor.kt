package hu.bme.aut.android.chatApp.domain

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import hu.bme.aut.android.chatApp.ChatApplication
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.Network.addNewRegisteredUser
import hu.bme.aut.android.chatApp.ui.Register.RegisterFragment
import javax.inject.Inject

class UserInteractor @Inject constructor() {

    fun saveRegisteredUser(user : User) {
       /* addNewRegisteredUser(user.userName, user.password, user.profilePicture)
        ChatApplication.userid++
        ChatApplication.usersList.add(user)*/
        Users.add(user)
        logUsers()
    }

    fun getUsers() : List<User>{
       // return ChatApplication.usersList
        logUsers()
        return Users
    }

    private fun logUsers(){
        for(user in Users)
            Log.i("user: ", user.toString())
    }
}