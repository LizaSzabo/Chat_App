package hu.bme.aut.android.chat_app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.amplifyframework.core.Amplify
import hu.bme.aut.android.chat_app.ChatApplication.Companion.allConversationList
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Network.initializeUserData
import hu.bme.aut.android.chat_app.Network.querys
import hu.bme.aut.android.chat_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var fragmentmanager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      /*  Amplify.DataStore.clear(
            {
                Amplify.DataStore.start(
                    {
                        Log.i("MyAmplifyApp", "DataStore started")
                    },
                    { Log.e("MyAmplifyApp", "Error starting DataStore", it) }
                )

            },
            { Log.e("MyAmplifyApp", "Error clearing DataStore", it) }
        )

        Thread.sleep(10000)*/

        val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/addprofile")
        val b: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        querys(b)
        val intentChatWindowService = Intent(
            this, FloatingService::class.java
        )

        startService(intentChatWindowService)

}

    override fun onStop() {
        Amplify.DataStore.clear(
            {
                Amplify.DataStore.start(
                    {
                        Log.i("MyAmplifyApp", "DataStore started")
                    },
                    { Log.e("MyAmplifyApp", "Error starting DataStore", it) }
                )

            },
            { Log.e("MyAmplifyApp", "Error clearing DataStore", it) }
        )
        super.onStop()
    }
}