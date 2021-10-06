package hu.bme.aut.android.chatApp.ui.Register

sealed class RegisterViewState

object Initial: RegisterViewState()

object RegistrationError : RegisterViewState()

object RegistrationCancelled : RegisterViewState()

object RegistrationSuccess : RegisterViewState()
