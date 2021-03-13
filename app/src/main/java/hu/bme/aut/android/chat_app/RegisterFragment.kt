package hu.bme.aut.android.chat_app

import android.R.attr
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.chat_app.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {


    private lateinit var fragmentBinding: FragmentRegisterBinding
    private val PICK_IMAGE = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding =FragmentRegisterBinding.inflate(inflater, container, false)

        fragmentBinding.buttonRegisterOk.setOnClickListener(View.OnClickListener { Registration() })
        fragmentBinding.ivAddPicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        return fragmentBinding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                // update the preview image in the layout
                fragmentBinding.ivAddPicture.setImageURI(selectedImageUri)
            }
        }
    }

    private fun Registration(){
        if(ValidateRegistration()){
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }
       }

    private fun ValidateRegistration(): Boolean{

        if(fragmentBinding.tvTextUserName.text.toString().isEmpty()){
            fragmentBinding.tvTextUserName.error = getString(R.string.user_name_required)
            return false
        }
        if( fragmentBinding.tvTextPassword.text.toString().isEmpty()){
            fragmentBinding.tvTextPassword.error = getString(R.string.pass_required)
            return false
        }
        if(fragmentBinding.tvTextPassword2.text.toString() != fragmentBinding.tvTextPassword.text.toString()){
            fragmentBinding.tvTextPassword2.error = getString(R.string.pass_confirmation_failed)
            return false
        }
        return true
    }
}