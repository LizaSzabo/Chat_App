package hu.bme.aut.android.chat_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.chat_app.databinding.FragmentEditProfileBinding
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding

class EditProfileFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentEditProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentEditProfileBinding.inflate(inflater, container, false)

        fragmentBinding.btnEditUserName.setOnClickListener(View.OnClickListener { openDialog() })
        fragmentBinding.btnChanePass.setOnClickListener({openChangePassDialog()})

        return fragmentBinding.root
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