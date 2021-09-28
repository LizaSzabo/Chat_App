package hu.bme.aut.android.chatApp.domain

import android.graphics.Bitmap
import hu.bme.aut.android.chatApp.Network.updateUserPicture
import javax.inject.Inject

class EditProfileInteractor @Inject constructor() {

    fun updateUser(picture: Bitmap) {
       updateUserPicture(picture)
    }
}