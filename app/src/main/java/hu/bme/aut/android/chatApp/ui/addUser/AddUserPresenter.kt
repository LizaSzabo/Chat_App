package hu.bme.aut.android.chatApp.ui.addUser

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.domain.ConversationInteractor
import hu.bme.aut.android.chatApp.domain.UserInteractor
import javax.inject.Inject

class AddUserPresenter @Inject constructor(
    private val userInteractor: UserInteractor,
    private val conversationInteractor: ConversationInteractor
) {

    suspend fun addUserToConversation(userName : String, conversation : Conversation) : Boolean = withIOContext {
        userInteractor.addConversationToUser(userName, conversation) && conversationInteractor.addUserToConversation(userName, conversation)
    }

    suspend fun existsUserName(userName: String) : Boolean = withIOContext {
        userInteractor.existsUserName(userName)
    }

    suspend fun isUserNameAdded(userName: String, conversation: Conversation) : Boolean = withIOContext {
        conversationInteractor.isUserNameAdded(userName, conversation)
    }
}