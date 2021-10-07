package hu.bme.aut.android.chatApp.ui.EditProfile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditProfileBinding.bind(view)
        fragmentBinding = binding

        fragmentBinding.btnEditUserName.setOnClickListener { viewModel.openDialog(parentFragmentManager, fragmentBinding) }
        fragmentBinding.btnChanePass.setOnClickListener { viewModel.openChangePassDialog(parentFragmentManager) }

        fragmentBinding.imageButtonEditProfile.setOnClickListener {
            openSomeActivityForResult()
        }
        fragmentBinding.tvUserName.text = currentUser?.userName
        fragmentBinding.imageButtonEditProfile.setImageBitmap(currentUser?.profilePicture)
    }


    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                val yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImageUri)
                val resized = yourBitmap.resizeByHeight(fragmentBinding.imageButtonEditProfile.layoutParams.height)
                //currentUser?.profilePicture = resized

                viewModel.updateUserProfileImage(resized)

                fragmentBinding.imageButtonEditProfile.setImageBitmap(resized)
            }
        }
    }

    private fun openSomeActivityForResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
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
            UserProfileUpdateError -> {
                Toast.makeText(context, "Profile picture update failed!", Toast.LENGTH_LONG).show()
            }
            UserProfileUpdateSuccess -> {
                Toast.makeText(context, "Profile picture successfully updated!", Toast.LENGTH_LONG).show()
            }
        }.exhaustive
    }

}