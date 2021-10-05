package hu.bme.aut.android.chatApp.ui.editusername

sealed class EditUserNameViewState

object Initial : EditUserNameViewState()

object EditUserNameSuccess : EditUserNameViewState()

object EditUserNameError: EditUserNameViewState()

object UpdateCancelled : EditUserNameViewState()