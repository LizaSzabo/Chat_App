package hu.bme.aut.android.chat_app

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
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
        val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")
        var convers =  mutableListOf(
            Conversation(
                "first", "private", mutableListOf<Message>(
                    Message(
                        "User1",
                        "second",
                        "Hello",
                        "2021.04.01 14:12"
                    ), Message("User1", "second", "Szia", "2021.04.01 14:12"), Message(
                        "User2",
                        "first",
                        "Hello",
                        "2021.04.01 14:12"
                    )
                ), uri, false
            )
        )

        var convers2 =  mutableListOf(
            Conversation(
                "first", "private", mutableListOf<Message>(
                    Message(
                        "User1",
                        "second",
                        "Hello",
                        "2021.04.01 14:12"
                    ), Message("User1", "second", "Szia", "2021.04.01 14:12"), Message(
                        "User2",
                        "first",
                        "Hello",
                        "2021.04.01 14:12"
                    )
                ), uri, false
            ),
            Conversation(
                "second", "private", mutableListOf<Message>(
                    Message(
                        "User1",
                        "second",
                        "Hello",
                        "2021.04.01 14:12"
                    ), Message("User1", "second", "Szia", "2021.04.01 14:12"), Message(
                        "User2",
                        "first",
                        "Hello",
                        "2021.04.01 14:12"
                    )
                ), uri, false
            )
        )
        var yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        usersList.add(User("User1", "pass",yourBitmap, convers))
        usersList.add(User("User2", "pass", yourBitmap, convers2))
    }

    override fun setupInjector() {
        injector = DaggerAppComponent.create()
    }

}