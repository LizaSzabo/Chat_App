package hu.bme.aut.android.chat_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.databinding.FragmentEditProfileBinding
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding

class EditProfileFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentEditProfileBinding
    private val PICK_IMAGE = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentEditProfileBinding.inflate(inflater, container, false)

        fragmentBinding.btnEditUserName.setOnClickListener(View.OnClickListener { openDialog() })
        fragmentBinding.btnChanePass.setOnClickListener({openChangePassDialog()})

        fragmentBinding.imageButtonEditProfile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        fragmentBinding.tvUserName.text = currentUser?.userName
        return fragmentBinding.root
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
    private fun openDialog() {
        val CreateFragment = EditUserNameDialog()
        CreateFragment.show(parentFragmentManager, "")
    }

    private fun openChangePassDialog() {
        val CreateFragment = ChangePassDialog()
        CreateFragment.show(parentFragmentManager, "")
    }


}