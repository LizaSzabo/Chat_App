package hu.bme.aut.android.chatApp.ui.loading

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentLoadingBinding
import hu.bme.aut.android.chatApp.databinding.FragmentLoginBinding

class LoadingFragment : RainbowCakeFragment<LoadingViewState, LoadingViewModel>() {

    override fun getViewResource() = R.layout.fragment_loading
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var fragmentBinding : FragmentLoadingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoadingBinding.bind(view)
        fragmentBinding = binding

        viewModel.initData()
    }


    override fun render(viewState: LoadingViewState) {
        when (viewState) {
            Initial -> {

            }
        }.exhaustive
    }

    override fun onEvent(event: OneShotEvent) {
        when(event){
            LoadingViewModel.DataStoreStarted -> {
                val action = LoadingFragmentDirections.actionLoadingFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }
}