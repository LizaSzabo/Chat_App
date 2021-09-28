package hu.bme.aut.android.chatApp.ui.EditProfile

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentEditProfileBinding


class EditProfileFragment : RainbowCakeFragment<EditProfileViewState, EditProfileViewModel>() {

    override fun getViewResource() = R.layout.fragment_edit_profile
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var fragmentBinding: FragmentEditProfileBinding
    private val pickImage = 101

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditProfileBinding.bind(view)
        fragmentBinding = binding

        fragmentBinding.btnEditUserName.setOnClickListener { viewModel.openDialog(parentFragmentManager, fragmentBinding) }
        fragmentBinding.btnChanePass.setOnClickListener { viewModel.openChangePassDialog(parentFragmentManager) }

        fragmentBinding.imageButtonEditProfile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), this.pickImage)
        }
        fragmentBinding.tvUserName.text = currentUser?.userName
        fragmentBinding.imageButtonEditProfile.setImageBitmap(currentUser?.profilePicture)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage) {
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                var yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImageUri)
                val resized = yourBitmap.resizeByHeight(fragmentBinding.imageButtonEditProfile.layoutParams.height)
                currentUser?.profilePicture = resized

                viewModel.updateUser(resized)

                fragmentBinding.imageButtonEditProfile.setImageBitmap(resized)
            }
        }
    }


    private fun Bitmap.resizeByHeight(height: Int): Bitmap {
        val ratio: Float = this.height.toFloat() / this.width.toFloat()
        val width: Int = Math.round(height / ratio)

        return Bitmap.createScaledBitmap(
            this,
            width,
            height,
            false
        )
    }

    override fun render(viewState: EditProfileViewState) {
        when (viewState) {
            Initial -> {

            }
            else -> {

            }
        }.exhaustive
    }

}