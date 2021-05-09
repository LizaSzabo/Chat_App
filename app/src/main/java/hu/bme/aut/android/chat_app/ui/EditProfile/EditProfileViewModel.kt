package hu.bme.aut.android.chat_app.ui.EditProfile

import android.graphics.Bitmap
import androidx.fragment.app.FragmentManager
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.Network.updateUserPicture
import hu.bme.aut.android.chat_app.ui.EditUserNameDialog
import hu.bme.aut.android.chat_app.databinding.FragmentEditProfileBinding
import hu.bme.aut.android.chat_app.ui.ChangePassDialog

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

    fun updateUser(picture: Bitmap){
        editProfilePresenter.updateUser(picture)
    }
}
