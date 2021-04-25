package hu.bme.aut.android.chat_app.Network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.DataStoreItemChange
import hu.bme.aut.android.chat_app.ChatApplication
import hu.bme.aut.android.chat_app.ChatApplication.Companion.allConversationList
import hu.bme.aut.android.chat_app.ChatApplication.Companion.allMessagesList
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.Model.User
import java.io.ByteArrayOutputStream

 fun UpdateUser(user: User) {

    /*Make persistent user from app user*/
    val u = com.amplifyframework.datastore.generated.model.User.builder()
        .userName(user.userName)
        .password(user.password)
        .profilePicture(bitMapToString(user.profilePicture))
        .build()

    /*delete old persistent user*/
    Amplify.DataStore.query(
        com.amplifyframework.datastore.generated.model.User::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.User.USER_NAME.eq(user.userName)),
        {
            if (it.hasNext()) {
                val post = it.next()
                Amplify.DataStore.delete(post,
                    { Log.i("MyAmplifyApp", "User deleted") },
                    { Log.e("MyAmplifyApp", "User Delete failed") }
                )
            }
        },
        { Log.e("MyAmplifyApp", "User Delete Query failed", it) }
    )

    /*Save persistent user with all of its conversations*/
    Amplify.DataStore.save(u,
        {
            Log.i("MyAmplifyApp", "User saved")

            for (conversation in user.conversations!!) {

                val c = com.amplifyframework.datastore.generated.model.Conversation.builder()
                    .name(conversation.name)
                    .type(conversation.type)
                    .picture(bitMapToString(conversation.picture))
                    .favourite(conversation.favourite)
                    .code(conversation.code)
                    .user(u)
                  //  .id(conversation.id)
                    .build()
                Amplify.DataStore.save(c,
                    { Log.i("MyAmplifyApp", "Conversation saved")

                        for(message in conversation.messages!!){
                            val m = com.amplifyframework.datastore.generated.model.Message.builder()
                                .sender(message.sender)
                                .content(message.content)
                                .date(message.date)
                                .conversation(c)
                                .receivers(message.receivers)
                                .build()
                            Amplify.DataStore.save(m,
                                { Log.i("MyAmplifyApp", "Message saved") },
                                { Log.e("MyAmplifyApp", "message not saved", it) }
                            )
                        }

                    },
                    { Log.e("MyAmplifyApp", "Conversation not saved", it) }
                )
            }

        },
        { Log.e("MyAmplifyApp", "User not saved", it) }
    )


}


fun updateUserPicture(picture: Bitmap){
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.User.USER_NAME.eq(
            currentUser?.userName)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .profilePicture(bitMapToString(picture))
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated a user picture") },
                    { Log.e("MyAmplifyApp", "Update failed", it) }
                )
            }
        },
        { Log.e("MyAmplifyApp", "Query failed", it) }
    )

}


fun updateUserName(newName : String, oldName: String?){
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.User.USER_NAME.eq(oldName)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .userName(newName)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated user name") },
                    { Log.e("MyAmplifyApp", "Update failed", it) }
                )
            }
        },
        { Log.e("MyAmplifyApp", "Query failed", it) }
    )
}


fun updateUserPassword(newPassword: String){
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.User.USER_NAME.eq(currentUser?.userName)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .password(newPassword)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated a user password") },
                    { Log.e("MyAmplifyApp", "Update failed", it) }
                )
            }
        },
        { Log.e("MyAmplifyApp", "Query failed", it) }
    )
}


fun addNewRegisteredUser(newUserName: String, newUserPassword: String, newUserPicture: Bitmap){

    val userBackend = com.amplifyframework.datastore.generated.model.User.builder()
        .userName(newUserName)
        .password(newUserPassword)
        .profilePicture(bitMapToString(newUserPicture))
        .build()

    Amplify.DataStore.save(userBackend,
        { Log.i("MyAmplifyApp", "Created a new user successfully") },
        { Log.e("MyAmplifyApp", "Error creating post") }
    )

    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        { matches ->
            while (matches.hasNext()) {
                val user = matches.next()
                Log.i("MyAmplifyApp", "Title: ${user.userName}")
            }
        },
        { Log.e("MyAmplifyApp", "Error retrieving posts", it) }
    )
}


fun initializeUserData(b: Bitmap){

    for(conv in ChatApplication.allConversationList){
        for(user in ChatApplication.usersList){
            if(conv.user.userName == user.userName){
                val decodedString: ByteArray = Base64.decode(conv.picture, Base64.DEFAULT)
                var decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                if(decodedByte == null) decodedByte = b


                val messageList: MutableList<Message> = mutableListOf()

                for(message in allMessagesList){
                    val mess = Message(message.sender, message.receivers, message.content, message.date)
                    if((message.conversation.code == conv.code) && (!messageList.contains(mess)))
                        messageList.add(mess)
                }
                
                user.conversations?.add(Conversation(conv.id, conv.name, conv.type, messageList, decodedByte, conv.favourite, conv.code))
            }
        }
    }
}

fun observeData(){
    Amplify.DataStore.observe(com.amplifyframework.datastore.generated.model.User::class.java,
        { Log.i("MyAmplifyApp", "Observation began") },
        {

        },
        { Log.e("MyAmplifyApp", "Observation failed", it) },
        { Log.i("MyAmplifyApp", "Observation complete") }
    )
    Amplify.DataStore.observe(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        { Log.i("MyAmplifyApp", "Observation began") },
        {

        },
        { Log.e("MyAmplifyApp", "Observation failed", it) },
        { Log.i("MyAmplifyApp", "Observation complete") }
    )
    Amplify.DataStore.observe(com.amplifyframework.datastore.generated.model.Message::class.java,
        { Log.i("MyAmplifyApp", "Observation began") },
        {
            val post = it
            Log.i("MyAmplifyApp", "Post: $post")


        },
        { Log.e("MyAmplifyApp", "Observation failed", it) },
        { Log.i("MyAmplifyApp", "Observation complete") }
    )
}

fun querys(b: Bitmap){

    usersList.clear()
    allMessagesList.clear()
    allConversationList.clear()

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
                       // Log.i("MyAmplifyApp", "Conversation:${conversation.code} ${conversation.name} ${conversation.user.userName}")
                        ChatApplication.allConversationList.add(conversation)
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

    )

}


fun bitMapToString(bitmap: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}