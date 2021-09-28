package hu.bme.aut.android.chat_app.ui.Login

sealed class LoginViewState

object Initial : LoginViewState()

object UsersInitSuccess : LoginViewState()

object UsersInitError : LoginViewState()