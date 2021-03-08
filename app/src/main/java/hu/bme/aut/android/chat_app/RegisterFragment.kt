package hu.bme.aut.android.chat_app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding
import hu.bme.aut.android.chat_app.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {


    private lateinit var fragmentBinding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding =FragmentRegisterBinding.inflate(inflater, container, false)

        fragmentBinding.buttonRegisterOk.setOnClickListener(View.OnClickListener { Registration() })

        return fragmentBinding.root
    }

    private fun Registration(){
        if(ValidateRegistration()){
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }
       }

    private fun ValidateRegistration(): Boolean{

        if(fragmentBinding.tvTextUserName.text.toString().isEmpty()){
            Snackbar.make(fragmentBinding.root,"User Name is reqiured", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if( fragmentBinding.tvTextPassword.text.toString().isEmpty()){
            Snackbar.make(fragmentBinding.root,"Password is required", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if(fragmentBinding.tvTextPassword2.text.toString() != fragmentBinding.tvTextPassword.text.toString()){
            Snackbar.make(fragmentBinding.root,"Password confirmation failed",
                Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }

        return true
    }
}