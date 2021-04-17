package hu.bme.aut.android.chat_app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import hu.bme.aut.android.chat_app.ChatApplication.Companion.allConversationList
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var fragmentmanager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/addprofile")
        val b: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        for(conv in allConversationList){
            for(user in usersList){
                if(conv.user.userName == user.userName){
                    val decodedString: ByteArray = Base64.decode(conv.picture, Base64.DEFAULT)
                    var decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    if(decodedByte == null) decodedByte = b
                    user.conversations?.add(Conversation(conv.id, conv.name, conv.type, mutableListOf(), decodedByte, conv.favourite))
                }
            }
        }
    }
}