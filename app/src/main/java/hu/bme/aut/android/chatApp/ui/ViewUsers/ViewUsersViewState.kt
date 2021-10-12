package hu.bme.aut.android.chatApp.ui.ViewUsers

sealed class ViewUsersViewState

object Initial: ViewUsersViewState()

object UsersLoadError : ViewUsersViewState()

object UsersLoadSuccess : ViewUsersViewState()