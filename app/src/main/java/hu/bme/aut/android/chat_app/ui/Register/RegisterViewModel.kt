package hu.bme.aut.android.chat_app.ui.Register

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.amplifyframework.core.Amplify
import hu.bme.aut.android.chat_app.ChatApplication.Companion.userid
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentRegisterBinding
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(Initial){

    lateinit var fragmentBinding: FragmentRegisterBinding
    lateinit var context : Context
    lateinit var pictureUri : Uri

   fun Registration(
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

        val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/addprofile")
        var convers =  mutableListOf<Conversation>()


        var yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(
            context?.contentResolver,
            pictureUri
        )
        userid++
        val user: User = User(
            fragmentBinding.tvTextUserName.text.toString(),
            fragmentBinding.tvTextPassword.text.toString(),
            yourBitmap,
            convers
        )

        val userBackend = com.amplifyframework.datastore.generated.model.User.builder()
            .userName(fragmentBinding.tvTextUserName.text.toString())
            .password(fragmentBinding.tvTextPassword.text.toString())
            .profilePicture(BitMapToString(yourBitmap))
            .build()

        Amplify.DataStore.save(userBackend,
            { Log.i("MyAmplifyApp", "Created a new user successfully") },
            { Log.e("MyAmplifyApp", "Error creating post") }
        )

        Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
            { matches ->
                while (matches.hasNext()) {
                    val user = matches.next()
                    Log.i("MyAmplifyApp", "Title: ${user.userName}")
                }
            },
            { Log.e("MyAmplifyApp", "Error retrieving posts", it) }
        )
/*
        Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
            { matches ->
                while (matches.hasNext()) {
                    val post = matches.next()
                    Amplify.DataStore.delete(post,
                        { Log.i("MyAmplifyApp", "Deleted a post.") },
                        { Log.e("MyAmplifyApp", "Delete failed.", it) }
                    )
                }
            },
            { Log.e("MyAmplifyApp", "Query failed.", it) }
        )*/

        usersList.add(user)
        return true
    }

    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}
