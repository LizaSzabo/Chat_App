package hu.bme.aut.android.chatApp.ui.addUser

sealed class AddUserViewState

object Initial : AddUserViewState()

object UserAddedSuccess : AddUserViewState()

object UserAddedError : AddUserViewState()

object UserAddedCancel : AddUserViewState()