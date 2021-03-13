package hu.bme.aut.android.chat_app


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chat_app.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegisterOk.setOnClickListener(View.OnClickListener { Registration() })
    }

    private fun ValidateRegistration(): Boolean{

        if(binding.editTextUserName.text.toString().isEmpty()){
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.user_name_required), Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if( binding.editTextPassword.text.toString().isEmpty()){
            Snackbar.make(findViewById(android.R.id.content),getString(R.string.pass_required), Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if(binding.editTextPassword2.text.toString() != binding.editTextPassword.text.toString()){
            Snackbar.make(findViewById(android.R.id.content),getString(R.string.pass_confirmation_failed),
                Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }

        return true
    }

    private fun Registration(){
        val intent = Intent(this, LoginActivity::class.java)
        if (ValidateRegistration()){
            intent.putExtra("SnackBarRegisterText", "Registration succeeded")
            this.startActivity(intent)
        }
    }
}