package hu.bme.aut.android.chat_app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        fragmentBinding =FragmentLoginBinding.inflate(inflater, container, false)


        /*val snackText=intent.getStringExtra("SnackBarRegisterText")
        if (snackText != null) {
            Snackbar.make(findViewById(android.R.id.content),snackText, Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.GREEN)
                .setTextColor(R.color.colorPrimaryDark)
                .show()
        }*/

        fragmentBinding.buttonOk.background.alpha = 170
        fragmentBinding.buttonRegister.background.alpha = 100

        fragmentBinding.buttonRegister.setOnClickListener(View.OnClickListener { openRegisterActivity() })
        fragmentBinding.buttonOk.setOnClickListener(View.OnClickListener { openMessagesActivity() })

        return fragmentBinding.root
    }
    private fun openRegisterActivity(){
      // myContext.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RegisterFragment(), null).commit()
      val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)

    }

    private fun openMessagesActivity(){
        if(ValidateLogin()){
            val action = LoginFragmentDirections.actionLoginFragmentToMessagesFragment()
            findNavController().navigate(action)
        }
    }

    private fun ValidateLogin(): Boolean{
       if(fragmentBinding.editTextLoginName.text.toString().isEmpty()){
            Snackbar.make(fragmentBinding.root,"User Name is reqiured", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if( fragmentBinding.editTextLoginPassword.text.toString().isEmpty()){
            Snackbar.make(fragmentBinding.root,"Password is required", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if(!ValidUserAndPass()){
            Snackbar.make(fragmentBinding.root,"Wrong User Name or Password",
                Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }

        return true
    }
    private fun ValidUserAndPass():Boolean{
        return true;
    }


}