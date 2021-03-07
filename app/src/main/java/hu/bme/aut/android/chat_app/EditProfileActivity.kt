package hu.bme.aut.android.chat_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.chat_app.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}