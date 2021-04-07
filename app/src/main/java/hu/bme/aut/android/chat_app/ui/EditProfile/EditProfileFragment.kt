package hu.bme.aut.android.chat_app.ui.EditProfile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chat_app.ui.ChangePass.ChangePassDialog
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.EditUserNameDialog
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentEditProfileBinding


class EditProfileFragment : RainbowCakeFragment<EditProfileViewState, EditProfileViewModel>() {
    private lateinit var fragmentBinding: FragmentEditProfileBinding
    private val PICK_IMAGE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditProfileBinding.bind(view)
        fragmentBinding= binding

        fragmentBinding.btnEditUserName.setOnClickListener(View.OnClickListener { viewModel.openDialog(parentFragmentManager) })
        fragmentBinding.btnChanePass.setOnClickListener({viewModel.openChangePassDialog(parentFragmentManager)})

        fragmentBinding.imageButtonEditProfile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), this.PICK_IMAGE)
           // currentUser?.profilePicture = imageButtonEditProfile as Int
        }
        fragmentBinding.tvUserName.text = currentUser?.userName
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                // update the preview image in the layout
                fragmentBinding.imageButtonEditProfile.setImageURI(selectedImageUri)
            }
        }
    }

    override fun getViewResource() = R.layout.fragment_edit_profile

    override fun provideViewModel()= getViewModelFromFactory()

    override fun render(viewState: EditProfileViewState) {
        when(viewState){
          Initial -> {

          }
            else -> {

            }
        }.exhaustive
    }

}