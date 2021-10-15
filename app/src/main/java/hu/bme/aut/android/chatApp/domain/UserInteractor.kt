package hu.bme.aut.android.chatApp.domain

import android.graphics.Bitmap
import android.util.Log
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.Network.changeUserPasswordDb
import hu.bme.aut.android.chatApp.Network.getAllUsers
import hu.bme.aut.android.chatApp.Network.saveUser
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
        return Users
    }

    private fun logUsers() {
        for (user in Users)
            Log.i("user: ", user.toString())
    }

    fun changeUserPassword(newPassword: String): Boolean {
        val change = changeUserPasswordDb(currentUser!!, newPassword)
        Log.i("change", change.toString())
        return if(change) {
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
        for (user in Users) {
            if (user.id == currentUser?.id) {

                user.userName = newUserName
                currentUser?.userName = newUserName
                return true
            }
        }
        return false
    }

    fun updateUserProfilePicture(picture: Bitmap): Boolean {
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