package hu.bme.aut.android.chatApp.ui.EditProfile

import android.graphics.Bitmap
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.domain.EditProfileInteractor
import javax.inject.Inject

class EditProfilePresenter @Inject constructor(
    private val editProfileInteractor: EditProfileInteractor
) {
    suspend fun updateUser(picture: Bitmap) = withIOContext {
        editProfileInteractor.updateUser(picture)
    }
}