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
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryPredicates
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.DataStoreConfiguration
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.UpdateUser
import hu.bme.aut.android.chat_app.Network.initializeUserData
import hu.bme.aut.android.chat_app.Network.observeData
import hu.bme.aut.android.chat_app.Network.querys
import hu.bme.aut.android.chat_app.di.DaggerAppComponent
import timber.log.Timber


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
        val b: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        val convers =  mutableListOf(
            Conversation(
                "1",
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
                ), b, false, "1234"
            )
        )

       val convers2 =  mutableListOf(
           Conversation(
               "1", "first", "private", mutableListOf(
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
               ), b, false, "1234"
           ),
           Conversation(
               "2", "second", "private", mutableListOf(
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
               ), b, false, "1234"
           )
       )
        val yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
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

       Amplify.DataStore.clear(
            {
               Amplify.DataStore.start(
                    { Log.i("MyAmplifyApp", "DataStore started")
                        },
                    { Log.e("MyAmplifyApp", "Error starting DataStore", it) }
                )

            },
            { Log.e("MyAmplifyApp", "Error clearing DataStore", it) }
        )
      /*  val user = builder()
            .userName("User1")
            .password("pass")
            .build()*/

   /*    Amplify.DataStore.save(user,
            { Log.i("MyAmplifyApp", "Created a new post successfully") },
            { Log.e("MyAmplifyApp", "Error creating post") }
        )*/
       /* Thread.sleep(20_000)

        Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
            { matches ->
                while (matches.hasNext()) {
                    val user = matches.next()
                    val decodedString: ByteArray = Base64.decode(user.profilePicture, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    var  conv2 : MutableList<Conversation> = mutableListOf()
                    if (user.conversations != null){
                    conv2.add(Conversation("1", user.conversations[0].name, user.conversations[0].type, mutableListOf(
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
                    ), b, user.conversations[0].favourite, "1234"
                    ))}
                    /*for (con in user.conversations){
                        conv2.add(Conversation(1, con.name, con.type, mutableListOf(
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
                        ), uri, con.favourite
                        ))
                    }*/
                    usersList.add(User(user.userName, user.password, decodedByte, conv2))
                    Log.i("MyAmplifyApp", "Title: ${user.userName}")
                }
                Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Conversation::class.java,
                    { matches ->
                        while (matches.hasNext()) {
                            val conversation = matches.next()
                            Log.i("MyAmplifyApp", "Conversation:${conversation.code} ${conversation.name} ${conversation.user.userName}")
                            allConversationList.add(conversation)
                        }
                        Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Message::class.java,
                            { matches ->
                                while (matches.hasNext()) {
                                    val message = matches.next()
                                 //   Log.i("MyAmplifyApp", "Message:${message.id} ${message.content} ${message.conversation.name}")
                                    allMessagesList.add(message)
                                }
                                initializeUserData(b)
                            },
                            { Log.e("MyAmplifyApp", "Query failed", it) }
                        )
                        //  initializeUserData(b)
                    },
                    { Log.e("MyAmplifyApp", "Query failed", it) }
                )


            },
            { Log.e("MyAmplifyApp", "Error retrieving posts", it) }

        )*/


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


        Thread.sleep(20_000)
        querys(b)
        observeData(b)



    }

    override fun setupInjector() {
        injector = DaggerAppComponent.create()
    }

}