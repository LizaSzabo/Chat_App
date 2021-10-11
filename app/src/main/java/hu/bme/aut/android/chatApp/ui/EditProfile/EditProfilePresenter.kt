package hu.bme.aut.android.chatApp.ui.EditProfile

import android.graphics.Bitmap
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.domain.UserInteractor
import javax.inject.Inject

class EditProfilePresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun updateUserProfilePicture(picture: Bitmap) : Boolean = withIOContext {
       userInteractor.updateUserProfilePicture(picture)
    }
}