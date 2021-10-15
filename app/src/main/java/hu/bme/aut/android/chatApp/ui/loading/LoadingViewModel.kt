package hu.bme.aut.android.chatApp.ui.loading

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.ui.login.UsersInitError
import hu.bme.aut.android.chatApp.ui.login.UsersInitSuccess

import javax.inject.Inject

class LoadingViewModel @Inject constructor(
    private val loadingPresenter: LoadingPresenter
) : RainbowCakeViewModel<LoadingViewState>(Initial) {


    fun initData() = execute {
        postEvent(DataStoreStarted)
    }

    object DataStoreStarted : OneShotEvent
}