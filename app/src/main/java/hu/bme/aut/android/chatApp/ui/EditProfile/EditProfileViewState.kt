package hu.bme.aut.android.chatApp.ui.EditProfile

sealed class EditProfileViewState

object Initial:EditProfileViewState()

object Loading: EditProfileViewState()

object DataReady:EditProfileViewState()

object NetworkError: EditProfileViewState()