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
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding
import java.util.*
import kotlin.properties.Delegates


class LoginFragment : Fragment(), AdapterView.OnItemSelectedListener{


    val args:  LoginFragmentArgs by navArgs()
    private lateinit var fragmentBinding: FragmentLoginBinding
    private var last_selected by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        fragmentBinding =FragmentLoginBinding.inflate(inflater, container, false)

        Log.i("pos", args.pos.toString())
        last_selected = args.pos
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


        val spinner: Spinner = fragmentBinding.spinnerLanguages
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.languages,
                android.R.layout.simple_spinner_item
            )
                .also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }
        spinner.onItemSelectedListener = this
        spinner.setSelection(args.pos)
        spinner.dropDownHorizontalOffset = -20


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
            Snackbar.make(fragmentBinding.root, "User Name is reqiured", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if( fragmentBinding.editTextLoginPassword.text.toString().isEmpty()){
            Snackbar.make(fragmentBinding.root, "Password is required", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if(!ValidUserAndPass()){
            Snackbar.make(
                fragmentBinding.root, "Wrong User Name or Password",
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }

        return true
    }
    private fun ValidUserAndPass():Boolean{
        return true;
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 -> setApplicationLocale("en")
            1 -> setApplicationLocale("hu")
           // else -> setApplicationLocale("en")
        }
        if(last_selected != position){
            last_selected = position
            val action = LoginFragmentDirections.actionLoginFragmentSelf(position)
            findNavController().navigate(action)
        }

       /* last_selected = activity?.intent?.extras?.getInt("pos")!!
        if(last_selected != position){
            last_selected = position
            val intent = activity?.intent
            intent?.putExtra("pos", last_selected)
            activity?.finish()
            startActivity(intent)
        }*/


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
}