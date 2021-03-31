package hu.bme.aut.android.chat_app

import android.app.Application
import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.dagger.RainbowCakeApplication
import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import co.zsmb.rainbowcake.timber.TIMBER
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.di.DaggerAppComponent
import timber.log.Timber

class ChatApplication : RainbowCakeApplication() {

    companion object {
         var usersList: MutableList<User> = mutableListOf()
            private set
        var currentUser: User? = null
        var currentConversation: Conversation? = null
    }

    override lateinit var injector: RainbowCakeComponent


    override fun onCreate() {
        super.onCreate()
        rainbowCake {
            logger = Loggers.TIMBER
            consumeExecuteExceptions = false
            isDebug = BuildConfig.DEBUG
        }
        Timber.plant(Timber.DebugTree())
        usersList.add(User("User1", "pass", R.id.profilepicture, mutableListOf(Conversation("first", "private"))))
        usersList.add(User("User2", "pass", R.id.profilepicture, mutableListOf(Conversation("second", "private"))))
    }

    override fun setupInjector() {
        injector = DaggerAppComponent.create()
    }

}