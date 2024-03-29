package hu.bme.aut.android.chatApp

import android.graphics.Bitmap
import android.util.Log
import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.dagger.RainbowCakeApplication
import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import co.zsmb.rainbowcake.timber.TIMBER
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.Message
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.di.DaggerAppComponent
import timber.log.Timber
import java.lang.Thread.sleep
import java.sql.Time


class ChatApplication : RainbowCakeApplication() {

    companion object {
         var usersList: MutableList<User> = mutableListOf()
            private set
        var currentUser: User? = null
        var currentConversation: Conversation? = null
        var allConversationList: MutableList<com.amplifyframework.datastore.generated.model.Conversation> = mutableListOf()
            private set

        var convid = 2
        var userid = 2
        var messageText = ""

        var Users: MutableList<User> = mutableListOf()
            private set
        var Conversations : MutableList<Conversation> = mutableListOf()
            private set
        var Messages = mutableListOf(
            Message("1","user1", "Hello!", "2021-10-10")
        )

        var newMessage : String = "..."
        var newMessageContent : String = "..."
        var newMessagePicture : Bitmap? = null

        var chatFragment = false

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

        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify")

        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }

    override fun setupInjector() {
        injector = DaggerAppComponent.create()
    }
}


