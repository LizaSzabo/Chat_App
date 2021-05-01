package hu.bme.aut.android.chat_app.ui.Register

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chat_app.ChatApplication.Companion.userid
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.addNewRegisteredUser
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentRegisterBinding
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(Initial){

    private lateinit var fragmentBinding: FragmentRegisterBinding
    lateinit var context : Context
    private lateinit var pictureUri : Uri

   fun registration(
       navController: NavController,
       binding: FragmentRegisterBinding,
       cxt: Context?,
       uri: Uri
   ){
       fragmentBinding = binding
       pictureUri = uri
       if (cxt != null) {
           context = cxt
       }
        if(validateRegistration()){
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            navController.navigate(action)
        }
    }

    private fun validateRegistration(): Boolean{

        if(fragmentBinding.tvTextUserName.text.toString().isEmpty()){
            fragmentBinding.tvTextUserName.error = context.getString(R.string.user_name_required)
            return false
        }
        for(user in usersList){
            if(user.userName == fragmentBinding.tvTextUserName.text.toString()){
                fragmentBinding.tvTextUserName.error = "User Name already exists"
                return false
            }
        }
        if( fragmentBinding.tvTextPassword.text.toString().isEmpty()){
            fragmentBinding.tvTextPassword.error = context.getString(R.string.pass_required)
            return false
        }
        if(fragmentBinding.tvTextPassword2.text.toString() != fragmentBinding.tvTextPassword.text.toString()){
            fragmentBinding.tvTextPassword2.error = context.getString(R.string.pass_confirmation_failed)
            return false
        }

        val convers =  mutableListOf<Conversation>()


        val yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(
            context.contentResolver,
            pictureUri
        )
        userid++
        val user = User(
            fragmentBinding.tvTextUserName.text.toString(),
            fragmentBinding.tvTextPassword.text.toString(),
            yourBitmap,
            convers
        )

        addNewRegisteredUser(fragmentBinding.tvTextUserName.text.toString(), fragmentBinding.tvTextPassword.text.toString(), yourBitmap )

        usersList.add(user)
        return true
    }

}
