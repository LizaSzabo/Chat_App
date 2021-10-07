package hu.bme.aut.android.chatApp.domain

import android.graphics.Bitmap
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Network.updateUserPicture
import javax.inject.Inject

class EditProfileInteractor @Inject constructor() {

    fun updateUser(picture: Bitmap) {
       //updateUserPicture(picture)
        for(user in Users){
            if(user.userName == currentUser?.userName)
                user.profilePicture = picture
        }
    }
}