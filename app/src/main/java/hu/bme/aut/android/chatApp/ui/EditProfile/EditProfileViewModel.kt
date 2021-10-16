package hu.bme.aut.android.chatApp.ui.EditProfile

import android.graphics.Bitmap
import androidx.fragment.app.FragmentManager
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.databinding.FragmentEditProfileBinding
import hu.bme.aut.android.chatApp.ui.changepassword.ChangePassDialog
import hu.bme.aut.android.chatApp.ui.editusername.EditUserNameDialog
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val editProfilePresenter: EditProfilePresenter
) : RainbowCakeViewModel<EditProfileViewState>(Initial) {

    fun openDialog(parentFragmentManager: FragmentManager, binding: FragmentEditProfileBinding) {
        if (viewState != UserProfileUpdateSaving) {
            val createFragment = EditUserNameDialog(binding)
            createFragment.show(parentFragmentManager, "")
        }
    }

    fun openChangePassDialog(parentFragmentManager: FragmentManager) {
        if (viewState != UserProfileUpdateSaving) {
            val createFragment = ChangePassDialog()
            createFragment.show(parentFragmentManager, "")
        }
    }

    fun updateUserProfileImage(picture: Bitmap) = execute {
        viewState = UserProfileUpdateSaving
        val updated = editProfilePresenter.updateUserProfilePicture(picture)
        viewState = if (updated) UserProfileUpdateSuccess else UserProfileUpdateError
    }
}
