package hu.bme.aut.android.chatApp


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import hu.bme.aut.android.chatApp.ChatApplication.Companion.messageText

import hu.bme.aut.android.chatApp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageText = "Waiting for new messages..."

        val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profile_picture")
        val b: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
       // querys(b)
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

        messageText = "Waiting for messages..."
        super.onStop()
    }
}