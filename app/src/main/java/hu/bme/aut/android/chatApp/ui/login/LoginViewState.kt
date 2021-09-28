package hu.bme.aut.android.chatApp.ui.login

sealed class LoginViewState

object Initial : LoginViewState()

object UsersInitSuccess : LoginViewState()

object UsersInitError : LoginViewState()