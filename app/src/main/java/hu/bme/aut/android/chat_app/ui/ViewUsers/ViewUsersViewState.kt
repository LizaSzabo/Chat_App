package hu.bme.aut.android.chat_app.ui.ViewUsers

sealed class ViewUsersViewState

object Initial: ViewUsersViewState()

object Loading: ViewUsersViewState()

object DataReady:ViewUsersViewState()

object NetworkError:ViewUsersViewState()