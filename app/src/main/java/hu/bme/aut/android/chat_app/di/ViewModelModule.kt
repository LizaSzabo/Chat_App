package hu.bme.aut.android.chat_app.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.aut.android.chat_app.ui.Chat.ChatViewModel
import hu.bme.aut.android.chat_app.ui.EditProfile.EditProfileViewModel
import hu.bme.aut.android.chat_app.ui.Login.LoginViewModel
import hu.bme.aut.android.chat_app.ui.Messages.MessagesViewModel
import hu.bme.aut.android.chat_app.ui.Register.RegisterViewModel
import hu.bme.aut.android.chat_app.ui.ViewUsers.ViewUsersViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindUserViewModel(userViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(userViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    abstract fun bindMessagesViewModel(userViewModel: MessagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(userViewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    abstract fun bindEditProfileViewModel(userViewModel: EditProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewUsersViewModel::class)
    abstract fun bindViewUsersViewModel(userViewModel: ViewUsersViewModel): ViewModel
}