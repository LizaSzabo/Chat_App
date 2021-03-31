package hu.bme.aut.android.chat_app.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.aut.android.chat_app.ui.Login.LoginViewModel

@Module
abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindUserViewModel(userViewModel: LoginViewModel): ViewModel

}