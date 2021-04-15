package hu.bme.aut.android.chat_app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.dagger.RainbowCakeApplication
import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import co.zsmb.rainbowcake.timber.TIMBER
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.User.builder
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
        var convid = 2
        var userid = 2
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
        val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/addprofile")
        val convers =  mutableListOf(
            Conversation(
                1,
                "first", "private", mutableListOf(
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

       val convers2 =  mutableListOf(
           Conversation(
               1, "first", "private", mutableListOf(
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
               2, "second", "private", mutableListOf(
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
        val yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        try {
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
        val user = builder()
            .userName("User1")
            .password("pass")
            .build()

   /*    Amplify.DataStore.save(user,
            { Log.i("MyAmplifyApp", "Created a new post successfully") },
            { Log.e("MyAmplifyApp", "Error creating post") }
        )*/

        Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
            { matches ->
                while (matches.hasNext()) {
                    val user = matches.next()
                    val decodedString: ByteArray = Base64.decode(user.profilePicture, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    usersList.add(User(user.userName, user.password, decodedByte, convers2))
                    Log.i("MyAmplifyApp", "Title: ${user.userName}")
                }
            },
            { Log.e("MyAmplifyApp", "Error retrieving posts", it) }
        )


       /* Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
            { matches ->
                while (matches.hasNext()) {
                    val post = matches.next()
                    Amplify.DataStore.delete(post,
                        { Log.i("MyAmplifyApp", "Deleted a post.") },
                        { Log.e("MyAmplifyApp", "Delete failed.", it) }
                    )
                }
            },
            { Log.e("MyAmplifyApp", "Query failed.", it) }
        )*/

    }

    override fun setupInjector() {
        injector = DaggerAppComponent.create()
    }

}