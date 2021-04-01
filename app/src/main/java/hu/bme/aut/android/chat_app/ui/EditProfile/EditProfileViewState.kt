package hu.bme.aut.android.chat_app.ui.EditProfile

sealed class EditProfileViewState

object Initial:EditProfileViewState()

object Loading: EditProfileViewState()

object DataReady:EditProfileViewState()

object NetworkError: EditProfileViewState()