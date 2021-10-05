package hu.bme.aut.android.chatApp.ui.changepassword

sealed class ChangePasswordViewState

object Initial : ChangePasswordViewState()

object ChangeError : ChangePasswordViewState()

object ChangeSuccess : ChangePasswordViewState()