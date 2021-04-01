package hu.bme.aut.android.chat_app.ui.Register

import android.content.Context
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(Initial){

    lateinit var fragmentBinding: FragmentRegisterBinding
    lateinit var context : Context

   fun Registration(navController: NavController, binding: FragmentRegisterBinding, cxt: Context?){
       fragmentBinding = binding
       if (cxt != null) {
           context = cxt
       }
        if(ValidateRegistration()){
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            navController.navigate(action)
        }
    }

    private fun ValidateRegistration(): Boolean{

        if(fragmentBinding.tvTextUserName.text.toString().isEmpty()){
            fragmentBinding.tvTextUserName.error = context.getString(R.string.user_name_required)
            return false
        }
        if( fragmentBinding.tvTextPassword.text.toString().isEmpty()){
            fragmentBinding.tvTextPassword.error = context.getString(R.string.pass_required)
            return false
        }
        if(fragmentBinding.tvTextPassword2.text.toString() != fragmentBinding.tvTextPassword.text.toString()){
            fragmentBinding.tvTextPassword2.error = context.getString(R.string.pass_confirmation_failed)
            return false
        }
        return true
    }
}
