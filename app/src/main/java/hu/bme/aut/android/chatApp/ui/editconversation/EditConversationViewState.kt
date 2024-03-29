package hu.bme.aut.android.chatApp.ui.editconversation

sealed class EditConversationViewState

object Initial : EditConversationViewState()

object UpdateSucceeded : EditConversationViewState()

object UpdateError : EditConversationViewState()