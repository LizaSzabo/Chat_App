package hu.bme.aut.android.chat_app.ui.ChangePass

sealed class ChangePassViewState

object Initial: ChangePassViewState()

object Loading: ChangePassViewState()

object DataReady:ChangePassViewState()

object NetworkError: ChangePassViewState()