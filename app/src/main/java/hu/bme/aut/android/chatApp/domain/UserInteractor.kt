package hu.bme.aut.android.chatApp.domain

import android.util.Log
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.User
import javax.inject.Inject

class UserInteractor @Inject constructor() {

    fun saveRegisteredUser(user : User) {
       /* addNewRegisteredUser(user.userName, user.password, user.profilePicture)
        ChatApplication.userid++
        ChatApplication.usersList.add(user)*/
        Users.add(user)
        logUsers()
    }

    fun getUsers() : List<User>{
       // return ChatApplication.usersList
        logUsers()
        return Users
    }

    private fun logUsers(){
        for(user in Users)
            Log.i("user: ", user.toString())
    }

    fun changeUserPassword(newPassword : String): Boolean{
        for(user in Users) {
            if (user.userName == currentUser?.userName) {
                user.password = newPassword
                currentUser?.password = newPassword
                return true
            }
        }
        return false
    }

    fun existsUserName(newUserName : String) : Boolean {
        for(user in Users)
            if (user.userName == newUserName)
                return true
        return false
    }

    fun updateUserName(newUserName : String): Boolean{
        for(user in Users) {
            if (user.userName == currentUser?.userName) {
                user.userName = newUserName
                currentUser?.userName = newUserName
                return true
            }
        }
        return false
    }
}