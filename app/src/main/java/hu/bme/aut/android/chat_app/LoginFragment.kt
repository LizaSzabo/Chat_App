package hu.bme.aut.android.chat_app

import android.content.Intent.getIntent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding
import hu.bme.aut.android.chat_app.ui.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*
import kotlin.properties.Delegates


class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>(), AdapterView.OnItemSelectedListener{

    val args:  LoginFragmentArgs by navArgs()
    private lateinit var fragmentBinding: FragmentLoginBinding
    private var last_selected by Delegates.notNull<Int>()



    override fun onViewCreated(view: View,  savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        fragmentBinding= binding

        Log.i("pos", args.pos.toString())
        last_selected = args.pos

        fragmentBinding.buttonOk.background.alpha = 170
        fragmentBinding.buttonRegister.background.alpha = 100

        fragmentBinding.buttonRegister.setOnClickListener(View.OnClickListener { openRegisterActivity() })
        fragmentBinding.buttonOk.setOnClickListener(View.OnClickListener { openMessagesActivity() })


        val spinner: Spinner = fragmentBinding.spinnerLanguages
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.languages,
                android.R.layout.simple_spinner_item
            )
                .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
        spinner.onItemSelectedListener = this
        spinner.setSelection(args.pos)
        spinner.dropDownHorizontalOffset = -20


    }

    private fun openRegisterActivity(){
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
           fragmentBinding.editTextLoginName.error = getString(R.string.user_name_required)
            return false
        }
        if( fragmentBinding.editTextLoginPassword.text.toString().isEmpty()){
            fragmentBinding.editTextLoginPassword.error = getString(R.string.pass_required)
            return false
        }
        if(!validUserAndPass()){
            Snackbar.make(
                fragmentBinding.root, getString(R.string.wrong_input),
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        return true
    }

    private fun validUserAndPass():Boolean{
        for(user in usersList){
            if(user.userName == (fragmentBinding.editTextLoginName.text.toString())){
                if(user.password == (fragmentBinding.editTextLoginPassword.text.toString())) {
                    currentUser = user
                    return true
                }
                return false
            }
        }
        return false
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 -> setApplicationLocale("en")
            1 -> setApplicationLocale("hu")
            else -> setApplicationLocale("en")
        }
        if(last_selected != position){
            last_selected = position
            val action = LoginFragmentDirections.actionLoginFragmentSelf(position)
            findNavController().navigate(action)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun setApplicationLocale(locale: String) {
        val resources = resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(Locale(locale.toLowerCase()))
        } else {
            config.locale = Locale(locale.toLowerCase())
        }
        resources.updateConfiguration(config, dm)

    }

    override fun getViewResource() = R.layout.fragment_login

    override fun provideViewModel()= getViewModelFromFactory()

    override fun render(viewState: LoginViewState) {
        when(viewState){
            Initial ->{

            }
            Loading ->{

            }
            DataReady -> {

            }
            NetworkError -> {

            }
        }.exhaustive
    }
}