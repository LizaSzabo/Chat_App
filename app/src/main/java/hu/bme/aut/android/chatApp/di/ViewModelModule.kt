package hu.bme.aut.android.chatApp.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.aut.android.chatApp.ui.Chat.ChatViewModel
import hu.bme.aut.android.chatApp.ui.EditProfile.EditProfileViewModel
import hu.bme.aut.android.chatApp.ui.login.LoginViewModel
import hu.bme.aut.android.chatApp.ui.Messages.MessagesViewModel
import hu.bme.aut.android.chatApp.ui.Register.RegisterViewModel
import hu.bme.aut.android.chatApp.ui.ViewUsers.ViewUsersViewModel
import hu.bme.aut.android.chatApp.ui.addConversation.AddConversationViewModel
import hu.bme.aut.android.chatApp.ui.addUser.AddUserViewModel
import hu.bme.aut.android.chatApp.ui.changepassword.ChangePasswordViewModel
import hu.bme.aut.android.chatApp.ui.editconversation.EditConversationViewModel
import hu.bme.aut.android.chatApp.ui.editusername.EditUserViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(AddConversationViewModel::class)
    abstract fun bindAddConversationViewModel(userViewModel: AddConversationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddUserViewModel::class)
    abstract fun bindAddUserViewModel(userViewModel: AddUserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel::class)
    abstract fun bindChangePasswordViewModel(userViewModel: ChangePasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditConversationViewModel::class)
    abstract fun bindEditConversationViewModel(userViewModel: EditConversationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditUserViewModel::class)
    abstract fun bindEditUserViewModel(userViewModel: EditUserViewModel): ViewModel
}