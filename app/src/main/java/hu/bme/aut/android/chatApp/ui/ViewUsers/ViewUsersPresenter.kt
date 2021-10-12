package hu.bme.aut.android.chatApp.ui.ViewUsers

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.domain.ConversationInteractor
import javax.inject.Inject

class ViewUsersPresenter @Inject constructor(
    private val conversationInteractor: ConversationInteractor
){

    suspend fun getUsersOfConversation(conversationId : String) : List<User> = withIOContext {
        conversationInteractor.getUsersOfConversation(conversationId)
    }
}