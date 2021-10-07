package hu.bme.aut.android.chatApp.ui.EditProfile

import android.graphics.Bitmap
import androidx.fragment.app.FragmentManager
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.ui.editusername.EditUserNameDialog
import hu.bme.aut.android.chatApp.databinding.FragmentEditProfileBinding
import hu.bme.aut.android.chatApp.ui.changepassword.ChangePassDialog

import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val editProfilePresenter: EditProfilePresenter
) : RainbowCakeViewModel<EditProfileViewState>(Initial){

     fun openDialog(parentFragmentManager: FragmentManager, binding : FragmentEditProfileBinding ) {
        val createFragment = EditUserNameDialog(binding)
        createFragment.show(parentFragmentManager, "")
    }

    fun openChangePassDialog(parentFragmentManager: FragmentManager) {
        val createFragment = ChangePassDialog()
        createFragment.show(parentFragmentManager, "")
    }

    fun updateUserProfileImage(picture: Bitmap) = execute {
        val updated = editProfilePresenter.updateUserProfilePicture(picture)
        viewState = if(updated) UserProfileUpdateSuccess else UserProfileUpdateError
    }
}
