package hu.bme.aut.android.chatApp.ui.EditProfile

import android.graphics.Bitmap
import hu.bme.aut.android.chatApp.Network.updateUserPicture
import javax.inject.Inject

class EditProfilePresenter @Inject constructor(){

    fun updateUser(picture: Bitmap){
       updateUserPicture(picture)
    }
}