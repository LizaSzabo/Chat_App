package hu.bme.aut.android.chatApp.ui.Messages

sealed class MessagesViewState

object Initial : MessagesViewState()

object ConversationLoadSuccess : MessagesViewState()

object ConversationLoadError : MessagesViewState()

object ConversationDeleteSuccess : MessagesViewState()

object ConversationDeleteError : MessagesViewState()

