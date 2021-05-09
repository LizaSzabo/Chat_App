package hu.bme.aut.android.chat_app.ui.Register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentRegisterBinding


class RegisterFragment :  RainbowCakeFragment<RegisterViewState, RegisterViewModel>() {

    private lateinit var fragmentBinding: FragmentRegisterBinding
    private val PICK_IMAGE = 1
    var uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/addprofile")


    override fun onViewCreated(view: View,  savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)
        fragmentBinding= binding

        fragmentBinding.buttonRegisterOk.setOnClickListener(View.OnClickListener { viewModel.registration(findNavController(), fragmentBinding, context, uri) })
        fragmentBinding.ivAddPicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        fragmentBinding.ivAddPicture.setImageURI(uri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                fragmentBinding.ivAddPicture.setImageURI(selectedImageUri)
                uri = selectedImageUri
            }
        }
    }


    override fun getViewResource() = R.layout.fragment_register

    override fun provideViewModel()= getViewModelFromFactory()

    override fun render(viewState: RegisterViewState) {
        when(viewState){
            Initial -> {

            }
            Loading -> {

            }
            DataReady -> {

            }
            NetworkError -> {

            }
        }.exhaustive
    }
}