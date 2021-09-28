package hu.bme.aut.android.chatApp.ui.login


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentLoginBinding
import kotlin.properties.Delegates


class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>(), AdapterView.OnItemSelectedListener {

    override fun getViewResource() = R.layout.fragment_login
    override fun provideViewModel() = getViewModelFromFactory()

    val args: LoginFragmentArgs by navArgs()
    private lateinit var fragmentBinding: FragmentLoginBinding
    private var lastSelected by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        fragmentBinding = binding

        lastSelected = args.pos

        fragmentBinding.buttonOk.background.alpha = 170
        fragmentBinding.buttonRegister.background.alpha = 100

        fragmentBinding.buttonRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
        fragmentBinding.buttonOk.setOnClickListener {
            if (isLoginValid()) {
                val action = LoginFragmentDirections.actionLoginFragmentToMessagesFragment()
                findNavController().navigate(action)
            }
        }

        val spinner: Spinner = fragmentBinding.spinnerLanguages
        context?.let {
            ArrayAdapter.createFromResource(it, R.array.languages, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
        }
        spinner.onItemSelectedListener = this
        spinner.setSelection(args.pos)
        spinner.dropDownHorizontalOffset = -20

        viewModel.init()
    }

    private fun isLoginValid(): Boolean {
        if (fragmentBinding.editTextLoginName.text.toString().isEmpty()) {
            fragmentBinding.editTextLoginName.error = getString(R.string.user_name_required)
            return false
        }
        if (fragmentBinding.editTextLoginPassword.text.toString().isEmpty()) {
            fragmentBinding.editTextLoginPassword.error = getString(R.string.pass_required)
            return false
        }
        if (!viewModel.validUserAndPass(
                fragmentBinding.editTextLoginName.text.toString(),
                fragmentBinding.editTextLoginPassword.text.toString()
            )
        ) {
            Snackbar.make(fragmentBinding.root, getString(R.string.wrong_input), Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        return true
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.onSpinnerItemSelected(position, lastSelected, findNavController(), context)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun render(viewState: LoginViewState) {
        when (viewState) {
            Initial -> {

            }
            UsersInitSuccess -> Unit
            UsersInitError -> Unit
        }.exhaustive
    }
}