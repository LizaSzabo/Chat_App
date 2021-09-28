package hu.bme.aut.android.chatApp.ui.ViewUsers

sealed class ViewUsersViewState

object Initial: ViewUsersViewState()

object Loading: ViewUsersViewState()

object DataReady:ViewUsersViewState()

object NetworkError:ViewUsersViewState()