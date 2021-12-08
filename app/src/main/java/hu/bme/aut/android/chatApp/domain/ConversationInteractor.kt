package hu.bme.aut.android.chatApp.domain

import android.graphics.Bitmap
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Conversations
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.Network.*
import javax.inject.Inject

class ConversationInteractor @Inject constructor() {

    fun getConversations(): List<Conversation> {
        val conversations = mutableListOf<Conversation>()
        for (conversation in Conversations)
            if (conversation.usersId.contains(currentUser?.id))
                conversations.add(conversation)
        return conversations
    }

    fun deleteConversation(conversation: Conversation): Boolean {

        if (conversation.usersId.contains(currentUser?.id)) {
            for (user in Users)
                if (user.id == currentUser?.id) {
                    user.conversationsId.remove(conversation.id)
                    conversation.usersId.remove(user.id)
                }
            currentUser?.conversationsId?.remove(conversation.id)

        }
        updateConversationsToUser(currentUser!!, currentUser?.conversationsId!!)
        deleteUserFromConversation(conversation, conversation.usersId)
        return true
    }

    fun addConversation(conversation: Conversation): Boolean {
        val added = saveConversation(conversation)
        if (added) Conversations.add(conversation)

        for (user in Users) {
            if (conversation.usersId.contains(user.id)) {
                val conversationsIdList = user.conversationsId
                val addedToUser = updateConversationsToUser(user, conversationsIdList)
                if (addedToUser) user.conversationsId.add(conversation.id)
            }
        }
        return added
    }

    fun updateConversationName(conversation: Conversation, conversationNewName: String): Boolean {
        changeConversationName(conversation, conversationNewName)
        for (c in Conversations)
            if (c.id == conversation.id) {
                c.name = conversationNewName
                return true
            }
        return false
    }

    fun updateConversationImage(conversation: Conversation, conversationPicture: Bitmap): Boolean {
        changeConversationImage(conversation, conversationPicture)
        for (c in Conversations)
            if (c.id == conversation.id) {
                c.picture = conversationPicture
                currentConversation?.picture = conversationPicture
                return true
            }
        return false
    }

    fun updateConversationFavourite(conversation: Conversation): Boolean {
        changeConversationFavourite(conversation)
        for (c in Conversations)
            if (c.id == conversation.id) {
                c.favourite = conversation.favourite
                currentConversation?.favourite = conversation.favourite
                return true
            }
        return false
    }

    fun addUserToConversation(userName: String, conversation: Conversation): Boolean {
        var userId = "-1"
        for (user in Users)
            if (user.userName == userName)
                userId = user.id
        for (c in Conversations)
            if (c.id == conversation.id) {
                c.usersId.add(userId)
                changeUsersId(c, c.usersId)
            }
        return true
    }

    fun isUserNameAdded(userName: String, conversation: Conversation): Boolean {
        var userId = "-1"
        for (user in Users)
            if (user.userName == userName)
                userId = user.id
        for (c in Conversations)
            if (c.id == conversation.id) {
                if (c.usersId.contains(userId))
                    return true
            }
        return false
    }

    fun getUsersOfConversation(conversationId: String): List<User> {
        val users = mutableListOf<User>()
        for (conversation in Conversations)
            if (conversation.id == conversationId)
                for (userId in conversation.usersId)
                    for (user in Users)
                        if (user.id == userId)
                            users.add(user)
        return users
    }
}