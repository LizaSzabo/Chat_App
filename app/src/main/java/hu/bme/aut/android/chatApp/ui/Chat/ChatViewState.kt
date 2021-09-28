package hu.bme.aut.android.chatApp.ui.Chat

sealed class ChatViewState

object Initial: ChatViewState()

object Loading: ChatViewState()

object DataReady: ChatViewState()

object NetworkError: ChatViewState()