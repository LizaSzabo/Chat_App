package hu.bme.aut.android.chat_app

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chat_app.databinding.ActivityMessagesBinding
import kotlinx.android.synthetic.main.activity_messages.*


class MessagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val snackText=intent.getStringExtra("SnackBarLoginText")
        if (snackText != null) {
            Snackbar.make(findViewById(android.R.id.content), snackText, Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.GREEN)
                .show()
        }

        binding.imageButtonWrite.setOnClickListener(View.OnClickListener { openChatActivity() })
        binding.imageButtonProfile.setOnClickListener(View.OnClickListener { openEditProfileActivity() })
    }

    private fun openChatActivity() {
        val intent = Intent(this, ChatActivity::class.java)
        this.startActivity(intent)
    }

    private fun openEditProfileActivity() {
        val intent = Intent(this, EditProfileActivity::class.java)
        this.startActivity(intent)
    }
}