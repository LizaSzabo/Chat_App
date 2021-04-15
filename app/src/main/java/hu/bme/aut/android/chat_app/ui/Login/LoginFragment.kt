package hu.bme.aut.android.chat_app.ui.Login


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
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding
import kotlin.properties.Delegates


class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>(), AdapterView.OnItemSelectedListener{

    val args: LoginFragmentArgs by navArgs()
    private lateinit var fragmentBinding: FragmentLoginBinding
    private var last_selected by Delegates.notNull<Int>()

    override fun onViewCreated(view: View,  savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        fragmentBinding= binding

        last_selected = args.pos

        fragmentBinding.buttonOk.background.alpha = 170
        fragmentBinding.buttonRegister.background.alpha = 100

        fragmentBinding.buttonRegister.setOnClickListener {
            viewModel.openRegisterActivity(
                findNavController()
            )
        }
        fragmentBinding.buttonOk.setOnClickListener {
            viewModel.openMessagesActivity(
                findNavController(),
                fragmentBinding,
                context
            )
        }


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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.onSpinnerItemSelected(position, last_selected, findNavController(), context)

    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
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