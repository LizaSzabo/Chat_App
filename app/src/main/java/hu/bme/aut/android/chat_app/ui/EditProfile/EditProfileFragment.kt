package hu.bme.aut.android.chat_app.ui.EditProfile

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentEditProfileBinding


class EditProfileFragment : RainbowCakeFragment<EditProfileViewState, EditProfileViewModel>() {
    private lateinit var fragmentBinding: FragmentEditProfileBinding
    private val PICK_IMAGE = 101

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditProfileBinding.bind(view)
        fragmentBinding= binding

        fragmentBinding.btnEditUserName.setOnClickListener(View.OnClickListener {
            viewModel.openDialog(parentFragmentManager, fragmentBinding)
        })
        fragmentBinding.btnChanePass.setOnClickListener({viewModel.openChangePassDialog(parentFragmentManager)})

        fragmentBinding.imageButtonEditProfile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), this.PICK_IMAGE)
           // currentUser?.profilePicture = imageButtonEditProfile as Int
        }
        fragmentBinding.tvUserName.text = currentUser?.userName
        fragmentBinding.imageButtonEditProfile.setImageBitmap(currentUser?.profilePicture)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                var yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImageUri)
                val resized = yourBitmap.resizeByHeight( fragmentBinding.imageButtonEditProfile.layoutParams.height)
                currentUser?.profilePicture = resized
                fragmentBinding.imageButtonEditProfile.setImageBitmap(resized)
            }
        }
    }

    private fun Bitmap.resizeByHeight(height:Int):Bitmap{
        val ratio:Float = this.height.toFloat() / this.width.toFloat()
        val width:Int = Math.round(height / ratio)

        return Bitmap.createScaledBitmap(
            this,
            width,
            height,
            false
        )
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