package hu.bme.aut.android.chat_app.Network

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
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
                    .user(u)
                  //  .id(conversation.id)
                    .build()
                Amplify.DataStore.save(c,
                    { Log.i("MyAmplifyApp", "Conversation saved") },
                    { Log.e("MyAmplifyApp", "Conversation not saved", it) }
                )
            }
        },
        { Log.e("MyAmplifyApp", "User not saved", it) }
    )
}


fun bitMapToString(bitmap: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}