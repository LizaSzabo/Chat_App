package hu.bme.aut.android.chat_app.ui.EditProfile

import android.graphics.Bitmap
import hu.bme.aut.android.chat_app.Network.updateUserPicture
import javax.inject.Inject

class EditProfilePresenter @Inject constructor(){

    fun updateUser(picture: Bitmap){
       updateUserPicture(picture)
    }
}