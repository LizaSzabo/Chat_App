package hu.bme.aut.android.chatApp.domain

import android.graphics.Bitmap
import android.util.Log
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.Network.*
import javax.inject.Inject

class UserInteractor @Inject constructor() {

    fun saveRegisteredUser(user: User): Boolean {
        Users.add(user)
        logUsers()
        saveUser(user)
        return true
    }

    fun getUsers(): List<User> {
        logUsers()
        getAllUsers()
        getAllConversations()
        getAllMessages()
        return Users
    }

    private fun logUsers() {
        for (user in Users)
            Log.i("user: ", user.toString())
    }

    fun changeUserPassword(newPassword: String): Boolean {
        val change = changeUserPasswordDb(currentUser!!, newPassword)
        Log.i("change password", change.toString())
        return if (change) {
            for (user in Users) {
                if (user.userName == currentUser?.userName) {
                    user.password = newPassword
                    currentUser?.password = newPassword
                }
            }
            true
        } else false
    }

    fun existsUserName(newUserName: String): Boolean {
        for (user in Users)
            if (user.userName == newUserName)
                return true
        return false
    }

    fun updateUserName(newUserName: String): Boolean {
        val change = changeUserName(currentUser!!, newUserName)

            for (user in Users) {
                if (user.id == currentUser?.id) {

                    user.userName = newUserName
                    currentUser?.userName = newUserName
                }
            }

      return true
    }

    fun updateUserProfilePicture(picture: Bitmap): Boolean {
        val change = changeUserProfilePictureDb(currentUser!!, picture)
        Log.i("change picture", change.toString())
        return if (change) {
            for (user in Users) {
                if (user.userName == currentUser?.userName) {
                    user.profilePicture = picture
                    currentUser?.profilePicture = picture
                }
            }
            true
        } else false
    }

    fun addConversationToUser(userName: String, conversation: Conversation): Boolean {
        for (user in Users)
            if (user.userName == userName){
                val conversationList = user.conversationsId
                conversationList.add(conversation.id)
                val add = updateConversationsToUser(user, conversationList)
                if(add) {
                    user.conversationsId.add(conversation.id)
                    return true
                }
            }
        return false
    }
}