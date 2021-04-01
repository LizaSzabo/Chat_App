package hu.bme.aut.android.chat_app.ui.Messages

sealed class MessagesViewState

object Initial: MessagesViewState()

object Loading: MessagesViewState()

object DataReady: MessagesViewState()

object NetworkError: MessagesViewState()