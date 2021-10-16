package hu.bme.aut.android.chatApp.ui.EditProfile

sealed class EditProfileViewState

object Initial:EditProfileViewState()

object UserProfileUpdateSuccess : EditProfileViewState()

object UserProfileUpdateError : EditProfileViewState()

object UserProfileUpdateSaving : EditProfileViewState()