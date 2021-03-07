package hu.bme.aut.android.chat_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.chat_app.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}