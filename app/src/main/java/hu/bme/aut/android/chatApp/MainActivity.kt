package hu.bme.aut.android.chatApp


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.chatApp.ChatApplication.Companion.messageText
import hu.bme.aut.android.chatApp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageText = "Waiting for new messages..."

        checkAndStartService()

    }

    private fun checkAndStartService() {
        if (checkHasDrawOverlayPermissions()) {
            startService(Intent(this, FloatingService::class.java))
        } else {
            AlertDialog.Builder(this)
                .setMessage(
                    "For the app to use notifications and" +
                            " floating window enable " +
                            "'Allow over other apps' setting "
                )
                .setPositiveButton("Ok", null)
                .create().show()
        }
    }

    private fun checkHasDrawOverlayPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }

}