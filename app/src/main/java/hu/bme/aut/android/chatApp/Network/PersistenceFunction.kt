package hu.bme.aut.android.chatApp.Network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import hu.bme.aut.android.chatApp.Adapter_Rv.ChatAdapter
import hu.bme.aut.android.chatApp.ChatApplication
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Conversations
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Messages
import hu.bme.aut.android.chatApp.ChatApplication.Companion.Users
import hu.bme.aut.android.chatApp.ChatApplication.Companion.chatFragment
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.ChatApplication.Companion.newMessage
import hu.bme.aut.android.chatApp.ChatApplication.Companion.newMessageContent
import hu.bme.aut.android.chatApp.ChatApplication.Companion.newMessagePicture
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.Message
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.ui.Chat.ChatViewModel
import java.io.ByteArrayOutputStream

fun startDataStore(): Boolean {
    var ok = true
    Amplify.DataStore.clear(
        {
            Amplify.DataStore.start(
                {
                    Log.i("MyAmplifyApp", "DataStore started")
                },
                {
                    Log.e("MyAmplifyApp", "Error starting DataStore", it)
                    ok = false
                }
            )
        },
        {
            Log.e("MyAmplifyApp", "Error clearing DataStore", it)
            ok = false
        }
    )
    return ok
}

fun saveUsers() {
    for (u in ChatApplication.Users) {
        val user = com.amplifyframework.datastore.generated.model.User.builder()
            .modelId(u.id)
            .userName(u.userName)
            .password(u.password)
            .profilePicture(encodeImage(u.profilePicture))
            .conversations(u.conversationsId)
            .build()

        Amplify.DataStore.save(user,
            { Log.i("MyAmplifyApp", "Saved all users") },
            { Log.e("MyAmplifyApp", "Save all users failed", it) }
        )
    }
}

fun saveUser(u: User) {
    val user = com.amplifyframework.datastore.generated.model.User.builder()
        .modelId(u.id)
        .userName(u.userName)
        .password(u.password)
        .profilePicture(encodeImage(u.profilePicture))
        .conversations(u.conversationsId)
        .build()

    Amplify.DataStore.save(user,
        { Log.i("MyAmplifyApp", "Saved a user") },
        { Log.e("MyAmplifyApp", "Save user failed", it) }
    )
}

fun getAllUsers(): Boolean {
    var ok = true
    Log.i("MyAmplifyApp", "Load Users: ")
    Users.clear()
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        { users ->
            while (users.hasNext()) {
                val user = users.next()
                val decodedString: ByteArray = Base64.decode(user.profilePicture, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                val u = User(user.modelId, user.userName, user.password, decodedByte, user.conversations)
                Users.add(u)
                Log.i("MyAmplifyApp", "Title: ${user.userName}")
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun changeUserPasswordDb(user: User, newPassword: String): Boolean {
    var ok = true
    encodeImage(user.profilePicture)?.let { Log.i("user picture", it) }
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.User.MODEL_ID.eq(user.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .password(newPassword)
                    .build()
                Log.i("user picture", edited.profilePicture)
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated ${user.userName} password") },
                    { Log.e("MyAmplifyApp", "Update failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun changeUserProfilePictureDb(user: User, picture: Bitmap): Boolean {
    var ok = true
    encodeImage(user.profilePicture)?.let { Log.i("user picture", it) }
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.User.MODEL_ID.eq(user.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .profilePicture(encodeImage(picture))
                    .build()
                encodeImage(user.profilePicture)?.let { Log.i("user picture", it) }
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated ${user.userName} picture") },
                    { Log.e("MyAmplifyApp", "Update ${user.userName} picture failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun changeUserName(user: User, newUserName: String): Boolean {
    var ok = true
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.User.MODEL_ID.eq(user.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .userName(newUserName)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated ${user.userName} to ${edited.userName}") },
                    { Log.e("MyAmplifyApp", "Update ${user.userName} user name failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun updateConversationsToUser(user: User, conversationsId: MutableList<String>): Boolean {
    var ok = true
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.User.MODEL_ID.eq(user.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .conversations(conversationsId)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated ${user.userName} conversations list") },
                    { Log.e("MyAmplifyApp", "Update ${user.userName} conversations failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun getAllConversations(): Boolean {
    var ok = true
    Log.i("MyAmplifyApp", "Load Conversations: ")
    Conversations.clear()
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        { conversations ->
            while (conversations.hasNext()) {
                val conversation = conversations.next()
                val decodedString: ByteArray = Base64.decode(conversation.picture, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                val c = Conversation(
                    conversation.modelId,
                    conversation.name,
                    conversation.type,
                    conversation.messagesId,
                    conversation.usersId,
                    decodedByte,
                    conversation.favourite
                )
                Conversations.add(c)
                Log.i("MyAmplifyApp", "Title: ${conversation.name}")
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun saveConversation(c: Conversation): Boolean {
    var added = true

    val conversation = com.amplifyframework.datastore.generated.model.Conversation.builder()
        .modelId(c.id)
        .name(c.name)
        .type(c.type)
        .picture(encodeImage(c.picture))
        .favourite(c.favourite)
        .usersId(c.usersId)
        .messagesId(c.messagesId)
        .build()

    Amplify.DataStore.save(conversation,
        { Log.i("MyAmplifyApp", "Saved a conversation") },
        {
            Log.e("MyAmplifyApp", "Save conversation failed", it)
            added = false
        }
    )
    return added
}

fun deleteUserFromConversation(conversation: Conversation, updateUsers: MutableList<String>) {
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.Conversation.MODEL_ID.eq(conversation.id)),
        { conversations ->
            while (conversations.hasNext()) {
                val original = conversations.next()
                val edited = original.copyOfBuilder()
                    .usersId(updateUsers)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated ${original.name} users list") },
                    { Log.e("MyAmplifyApp", "Update ${original.name} users failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
        }
    )

}


fun changeConversationName(conversation: Conversation, newName: String): Boolean {
    var ok = true
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.Conversation.MODEL_ID.eq(conversation.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .name(newName)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated ${conversation.name} to ${edited.name}") },
                    { Log.e("MyAmplifyApp", "Update ${conversation.name} name failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun changeConversationImage(conversation: Conversation, picture: Bitmap): Boolean {
    var ok = true
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.Conversation.MODEL_ID.eq(conversation.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .picture(encodeImage(picture))
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated ${conversation.name} picture") },
                    { Log.e("MyAmplifyApp", "Update ${conversation.name} picture failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun changeConversationFavourite(conversation: Conversation): Boolean {
    var ok = true
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.Conversation.MODEL_ID.eq(conversation.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .favourite(conversation.favourite)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated ${conversation.name} favourite") },
                    { Log.e("MyAmplifyApp", "Update ${conversation.name} favourite failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}


fun changeUsersId(conversation: Conversation, usersId: MutableList<String>): Boolean {
    var ok = true
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.Conversation.MODEL_ID.eq(conversation.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .usersId(usersId)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated  ${conversation.name} users list") },
                    { Log.e("MyAmplifyApp", "Update  ${conversation.name} users failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun saveMessage(m: Message) {
    val message = com.amplifyframework.datastore.generated.model.Message.builder()
        .modelId(m.id)
        .sender(m.senderId)
        .content(m.content)
        .date(m.date)
        .build()

    Amplify.DataStore.save(message,
        { Log.i("MyAmplifyApp", "Saved a message") },
        { Log.e("MyAmplifyApp", "Save message failed", it) }
    )
}

fun addMessageToConversation(conversation: Conversation, messagesId: MutableList<String>): Boolean {
    var ok = true
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        Where.matches(com.amplifyframework.datastore.generated.model.Conversation.MODEL_ID.eq(conversation.id)),
        { matches ->
            if (matches.hasNext()) {
                val original = matches.next()
                val edited = original.copyOfBuilder()
                    .messagesId(messagesId)
                    .build()
                Amplify.DataStore.save(edited,
                    { Log.i("MyAmplifyApp", "Updated  ${conversation.name} messages list") },
                    { Log.e("MyAmplifyApp", "Update  ${conversation.name} messages failed", it) }
                )
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
    return ok
}

fun getAllMessages() {
    var ok = true
    Log.i("MyAmplifyApp", "Load Messages: ")
    Messages.clear()
    Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.Message::class.java,
        { messages ->
            while (messages.hasNext()) {
                val message = messages.next()
                val m = Message(
                    message.modelId,
                    message.sender,
                    message.content,
                    message.date
                )
                Messages.add(m)
                Log.i("MyAmplifyApp", "Title: ${message.content}")
            }
        },
        {
            Log.e("MyAmplifyApp", "Query failed", it)
            ok = false
        }
    )
}

fun observeNewMessages() {

    Amplify.DataStore.observe(com.amplifyframework.datastore.generated.model.Message::class.java,
        { Log.i("MyAmplifyApp", "Observation began") },
        {
            val message = it.item()
            for (user in Users) {
                if (user.id == it.item().sender) {
                    newMessagePicture = user.profilePicture
                    Log.i("MyAmplifyApp", "Message Picture:  $newMessagePicture")
                }
            }
            if (it.item().sender != currentUser?.id) {
                newMessageContent = it.item().content
            }

            Log.i("MyAmplifyApp", "Message: $message")
        },
        { Log.e("MyAmplifyApp", "Observation failed", it) },
        { Log.i("MyAmplifyApp", "Observation complete") }
    )
}

fun observeConversations() {

    Amplify.DataStore.observe(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        { Log.i("MyAmplifyApp", "Observation began") },
        {

            val conversation = it.item()
            if (currentUser?.conversationsId?.contains(conversation.modelId) == true)
                newMessage = newMessageContent
            Log.i("MyAmplifyApp", "Conversation $conversation")
        },
        { Log.e("MyAmplifyApp", "Observation failed", it) },
        { Log.i("MyAmplifyApp", "Observation complete") }
    )
}

fun observeNewMessage(viewModel: ChatViewModel, adapter: ChatAdapter) {

    Amplify.DataStore.observe(com.amplifyframework.datastore.generated.model.Message::class.java,
        { Log.i("MyAmplifyApp", "Observation began") },
        {

            val message = it.item()
            val modelMessage = Message(message.modelId, message.sender, message.content, message.date)

            if (!Messages.contains(modelMessage) && it.item().sender != currentUser?.id && chatFragment) {
                Messages.add(modelMessage)
                for (conversation in Conversations)
                    if (conversation.id == currentConversation?.id)
                        conversation.messagesId.add(message.modelId)
                currentConversation?.id?.let { it1 -> viewModel.loadAllMessages(adapter, it1) }
            }
            Log.i("MyAmplifyApp", "Conversation $message")
        },
        { Log.e("MyAmplifyApp", "Observation failed", it) },
        { Log.i("MyAmplifyApp", "Observation complete") }
    )
}

fun observeNewConversation() {

    Amplify.DataStore.observe(com.amplifyframework.datastore.generated.model.Conversation::class.java,
        { Log.i("MyAmplifyApp", "Observation began") },
        {

            val conversation = it.item()
            val decodedString: ByteArray = Base64.decode(conversation.picture, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            val modelConversation = Conversation(
                conversation.modelId,
                conversation.name,
                conversation.type,
                conversation.messagesId,
                conversation.usersId,
                decodedByte,
                conversation.favourite
            )
            if (!Conversations.contains(modelConversation) && conversation.usersId.contains(currentUser?.id)) {
                Conversations.add(modelConversation)
                currentUser?.conversationsId?.add(modelConversation.id)
            }
            Log.i("MyAmplifyApp", "Conversation $conversation")
        },
        { Log.e("MyAmplifyApp", "Observation failed", it) },
        { Log.i("MyAmplifyApp", "Observation complete") }
    )
}

private fun encodeImage(bm: Bitmap): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT);
}


