package hu.bme.aut.android.chat_app.ui.Chat

sealed class ChatViewState

object Initial: ChatViewState()

object Loading: ChatViewState()

object DataReady: ChatViewState()

object NetworkError: ChatViewState()