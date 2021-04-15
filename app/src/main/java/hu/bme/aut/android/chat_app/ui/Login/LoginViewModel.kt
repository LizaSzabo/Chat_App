package hu.bme.aut.android.chat_app.ui.Login

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chat_app.ChatApplication
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

@SuppressLint("StaticFieldLeak")
class LoginViewModel @Inject constructor(
    private val loginPresenter: LoginPresenter
) : RainbowCakeViewModel<LoginViewState>(Initial){

    private lateinit var fragmentBinding: FragmentLoginBinding
    private lateinit var context: Context
    private var lastSelected by Delegates.notNull<Int>()

    fun loadRates() = execute{
       // viewState = Loading
        viewState = try{
            DataReady
        }catch (e: Exception){
            NetworkError
        }
    }

     fun openRegisterActivity(navController: NavController){
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        navController.navigate(action)
    }

    fun openMessagesActivity(navController: NavController, binding: FragmentLoginBinding, cxt: Context?){
        fragmentBinding = binding
        if (cxt != null) {
            context = cxt
        }
        if(validateLogin()){
            val action = LoginFragmentDirections.actionLoginFragmentToMessagesFragment()
            navController.navigate(action)
        }
    }

    private fun validateLogin(): Boolean{
        if(fragmentBinding.editTextLoginName.text.toString().isEmpty()){
            fragmentBinding.editTextLoginName.error = context.getString(R.string.user_name_required)
            return false
        }
        if( fragmentBinding.editTextLoginPassword.text.toString().isEmpty()){
            fragmentBinding.editTextLoginPassword.error = context.getString(R.string.pass_required)
            return false
        }
        if(!validUserAndPass()){
            Snackbar.make(
                fragmentBinding.root, context.getString(R.string.wrong_input),
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        return true
    }

    private fun validUserAndPass():Boolean{
        for(user in ChatApplication.usersList){
            if(user.userName == (fragmentBinding.editTextLoginName.text.toString())){
                if(user.password == (fragmentBinding.editTextLoginPassword.text.toString())) {
                    ChatApplication.currentUser = user
                    return true
                }
                return false
            }
        }
        return false
    }

    fun onSpinnerItemSelected(position: Int, last_selected: Int, navController: NavController, cxt: Context?) {
        if (cxt != null) {
            context = cxt
        }
        when(position){
            0 -> setApplicationLocale("en")
            1 -> setApplicationLocale("hu")
            else -> setApplicationLocale("en")
        }
        if(last_selected != position){
            lastSelected = position
            val action = LoginFragmentDirections.actionLoginFragmentSelf(position)
            navController.navigate(action)
        }
    }

    private fun setApplicationLocale(locale: String) {
        val resources = context.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(Locale(locale.toLowerCase()))
        } else {
            config.locale = Locale(locale.toLowerCase())
        }
        resources.updateConfiguration(config, dm)
    }
}