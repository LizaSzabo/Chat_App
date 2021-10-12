package hu.bme.aut.android.chatApp.ui.addConversation

sealed class AddConversationViewState

object Initial : AddConversationViewState()

object ConversationAddSuccess : AddConversationViewState()

object ConversationAddError : AddConversationViewState()