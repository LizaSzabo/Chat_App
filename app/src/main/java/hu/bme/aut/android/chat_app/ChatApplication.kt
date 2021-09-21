package hu.bme.aut.android.chat_app

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.dagger.RainbowCakeApplication
import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import co.zsmb.rainbowcake.timber.TIMBER
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.Consumer
import com.amplifyframework.core.model.query.predicate.QueryPredicates
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.DataStoreChannelEventName
import com.amplifyframework.datastore.DataStoreConfiguration
import com.amplifyframework.datastore.DataStoreException
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.HubEvent
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.observeData
import hu.bme.aut.android.chat_app.Network.querys
import hu.bme.aut.android.chat_app.di.DaggerAppComponent
import timber.log.Timber
import java.util.*


class ChatApplication : RainbowCakeApplication() {

    companion object {
         var usersList: MutableList<User> = mutableListOf()
            private set
        var currentUser: User? = null
        var currentConversation: Conversation? = null
        var allConversationList: MutableList<com.amplifyframework.datastore.generated.model.Conversation> = mutableListOf()
            private set
        var allMessagesList: MutableList<com.amplifyframework.datastore.generated.model.Message> = mutableListOf()
            private set
        var convid = 2
        var userid = 2
        var update = false
        var messageText = ""
        var lastDate : String = ""
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
        val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profile_picture")
        val b: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

        try {
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSDataStorePlugin(DataStoreConfiguration.builder().
            syncExpression(com.amplifyframework.datastore.generated.model.Message::class.java){ QueryPredicates.all()}.build()))
            Amplify.configure(applicationContext)



            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }



        //querys(b)
        Amplify.Hub.subscribe(HubChannel.DATASTORE,
            { hubEvent: HubEvent<*> -> DataStoreChannelEventName.READY.equals(hubEvent.name) }
        ) { hubEvent: HubEvent<*>? ->
            Log.i("MyAmplifyApp", "AAAAAAAAAAAAAAAAAAAAAAAd")

        }

        observeData(b, this)
    }

    override fun setupInjector() {
        injector = DaggerAppComponent.create()
    }

}