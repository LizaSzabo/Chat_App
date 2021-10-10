package hu.bme.aut.android.chatApp.ui.Chat

sealed class ChatViewState

object Initial: ChatViewState()

object MessageLoadSuccess: ChatViewState()

object MessageLoadError: ChatViewState()

object MessageAddSuccess: ChatViewState()

object MessageAddError: ChatViewState()