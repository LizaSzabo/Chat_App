package hu.bme.aut.android.chatApp.domain

import android.graphics.Bitmap
import android.util.Log
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Conversations
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import javax.inject.Inject

class UserInteractor @Inject constructor() {

    fun saveRegisteredUser(user: User): Boolean {
        Users.add(user)
        logUsers()
        return true
    }

    fun getUsers(): List<User> {
        logUsers()
        return Users
    }

    private fun logUsers() {
        for (user in Users)
            Log.i("user: ", user.toString())
    }

    fun changeUserPassword(newPassword: String): Boolean {
        for (user in Users) {
            if (user.userName == currentUser?.userName) {
                user.password = newPassword
                currentUser?.password = newPassword
                return true
            }
        }
        return false
    }

    fun existsUserName(newUserName: String): Boolean {
        for (user in Users)
            if (user.userName == newUserName)
                return true
        return false
    }

    fun updateUserName(newUserName: String): Boolean {
        for (user in Users) {
            if (user.userName == currentUser?.userName) {

                for (conversation in Conversations)
                    if (conversation.usersName.contains(user.userName)) {
                        conversation.usersName.add( newUserName)
                        conversation.usersName.remove(user.userName)
                        Log.i("user", currentUser?.userName.toString())
                    }

                user.userName = newUserName
                currentUser?.userName = newUserName
                return true
            }
        }
        return false
    }

    fun updateUserProfilePicture(picture: Bitmap): Boolean {
        //updateUserPicture(picture)
        for (user in Users) {
            if (user.userName == currentUser?.userName) {
                user.profilePicture = picture
                currentUser?.profilePicture = picture
                return true
            }
        }
        return false
    }

    fun addConversationToUser(userName: String, conversation: Conversation): Boolean {
        for (user in Users)
            if (user.userName == userName)
                user.conversationsId.add(conversation.id)
        return true
    }
}