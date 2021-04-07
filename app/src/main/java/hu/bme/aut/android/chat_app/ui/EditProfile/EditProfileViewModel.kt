package hu.bme.aut.android.chat_app.ui.EditProfile

import androidx.fragment.app.FragmentManager
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.EditUserNameDialog
import hu.bme.aut.android.chat_app.ui.ChangePass.ChangePassDialog

import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val editProfilePresenter: EditProfilePresenter
) : RainbowCakeViewModel<EditProfileViewState>(Initial){

     fun openDialog(parentFragmentManager: FragmentManager) {
        val CreateFragment = EditUserNameDialog()
        CreateFragment.show(parentFragmentManager, "")
    }

    fun openChangePassDialog(parentFragmentManager: FragmentManager) {
        val CreateFragment = ChangePassDialog()
        CreateFragment.show(parentFragmentManager, "")
    }
}