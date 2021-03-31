package hu.bme.aut.android.chat_app.ui

sealed class LoginViewState

object Initial: LoginViewState()

object Loading: LoginViewState()

object DataReady: LoginViewState()

object NetworkError: LoginViewState()