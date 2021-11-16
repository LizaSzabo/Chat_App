package hu.bme.aut.android.chatApp.ui.Register

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentRegisterBinding


class RegisterFragment : RainbowCakeFragment<RegisterViewState, RegisterViewModel>() {

    override fun getViewResource() = R.layout.fragment_register
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var fragmentBinding: FragmentRegisterBinding
    var uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profile_picture")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)
        fragmentBinding = binding

        initListeners()
    }

    private fun initListeners() {
        fragmentBinding.buttonRegisterOk.setOnClickListener {
            if (isRegistrationValid()) {

                val yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)

                viewModel.saveRegisteredUser(
                    yourBitmap,
                    fragmentBinding.tvTextUserName.text.toString(),
                    fragmentBinding.tvTextPassword.text.toString()
                )
            }
        }

        fragmentBinding.ivAddPicture.setOnClickListener {
            openSomeActivityForImageResult()
        }

        fragmentBinding.ivAddPicture.setImageURI(uri)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                fragmentBinding.ivAddPicture.setImageURI(selectedImageUri)
                uri = selectedImageUri
            }
        }
    }

    private fun openSomeActivityForImageResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private fun isRegistrationValid(): Boolean {

        if (fragmentBinding.tvTextUserName.text.toString().isEmpty()) {
            fragmentBinding.tvTextUserName.error = context?.getString(R.string.user_name_required) ?: return false
        }
        if (fragmentBinding.tvTextPassword.text.toString().isEmpty()) {
            fragmentBinding.tvTextPassword.error = context?.getString(R.string.pass_required)
            return false
        }
        if (fragmentBinding.tvTextPassword2.text.toString() != fragmentBinding.tvTextPassword.text.toString()) {
            fragmentBinding.tvTextPassword2.error = context?.getString(R.string.pass_confirmation_failed)
            return false
        }

        return true
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is RegisterViewModel.UserAlreadyExists -> {
                fragmentBinding.tvTextUserName.error = "User Name already exists"
            }
            is RegisterViewModel.RegistrationSucceeded -> {
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun render(viewState: RegisterViewState) {
        when (viewState) {
            Initial -> {
                fragmentBinding.errorText.isVisible = false
            }
            RegistrationCancelled -> {
                fragmentBinding.errorText.isVisible = false
            }
            RegistrationError -> {
                fragmentBinding.errorText.isVisible = true
            }
            RegistrationSuccess -> {
            }
        }.exhaustive
    }

}