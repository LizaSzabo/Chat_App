package hu.bme.aut.android.chat_app

import android.app.Application
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.Model.User

class ChatApplication : Application() {

    companion object {
         var usersList: MutableList<User> = mutableListOf()
            private set
        var currentUser: User? = null
        var currentConversation: Conversation? = null
    }

    override fun onCreate() {
        super.onCreate()
        usersList.add(User("User1", "pass", R.id.profilepicture, mutableListOf(Conversation("first", "private"))))
        usersList.add(User("User2", "pass", R.id.profilepicture, mutableListOf(Conversation("second", "private"))))
    }

}