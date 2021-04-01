package hu.bme.aut.android.chat_app.ui.Register

sealed class RegisterViewState

object Initial: RegisterViewState()

object Loading: RegisterViewState()

object DataReady:RegisterViewState()

object NetworkError: RegisterViewState()