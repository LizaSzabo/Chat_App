package hu.bme.aut.android.chat_app.ui.Register

import android.content.Context
import android.net.Uri
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(Initial){

    lateinit var fragmentBinding: FragmentRegisterBinding
    lateinit var context : Context
    lateinit var pictureUri : Uri

   fun Registration(navController: NavController, binding: FragmentRegisterBinding, cxt: Context?, uri: Uri){
       fragmentBinding = binding
       pictureUri = uri
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

    /*    var convers =  mutableListOf(
            Conversation("first", "private", mutableListOf<Message>(
                Message("User1", "second", "Hello", "14:12"), Message("User1", "second", "Szia", "14:12"),
                Message("User2", "first", "Hello", "14:12")
            ))
        )*/
        val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/addprofile")
        val user: User = User(fragmentBinding.tvTextUserName.text.toString(), fragmentBinding.tvTextPassword.text.toString(), pictureUri , null)
        usersList.add(user)
        return true
    }
}
