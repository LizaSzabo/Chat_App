package hu.bme.aut.android.chat_app.ui

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import java.lang.Exception
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginPresenter: LoginPresenter
) : RainbowCakeViewModel<LoginViewState>(Initial){

    init{
       // viewState = Initial
    }

    fun loadRates() = execute{
        viewState = Loading
        viewState = try{
            DataReady
        }catch (e: Exception){
            NetworkError
        }
    }
}