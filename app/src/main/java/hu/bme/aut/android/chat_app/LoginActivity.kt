package hu.bme.aut.android.chat_app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.chat_app.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.reflect.KClass


class LoginActivity: AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_login)

       binding = ActivityLoginBinding.inflate(layoutInflater)
       setContentView(binding.root)

        val snackText=intent.getStringExtra("SnackBarRegisterText")
        if (snackText != null) {
            Snackbar.make(findViewById(android.R.id.content),snackText,Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.GREEN)
                .setTextColor(R.color.colorPrimaryDark)
                .show()
        }


        binding.buttonOk.background.alpha = 170
        binding.buttonRegister.background.alpha = 100

        binding.buttonRegister.setOnClickListener(View.OnClickListener { openRegisterActivity() })
        binding.buttonOk.setOnClickListener(View.OnClickListener { openMessagesActivity() })
    }
    private fun openRegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java)
        this.startActivity(intent)
    }

    private fun openMessagesActivity(){
        val intent = Intent(this, MessagesActivity::class.java)
        if(ValidateLogin()){
            intent.putExtra("SnackBarLoginText", "Login succeeded")
            this.startActivity(intent)
        }
    }

    private fun ValidateLogin(): Boolean{
        if(binding.editTextLoginName.text.toString().isEmpty()){
           Snackbar.make(findViewById(android.R.id.content),"User Name is reqiured",Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if( binding.editTextLoginPassword.text.toString().isEmpty()){
             Snackbar.make(findViewById(android.R.id.content),"Password is required",Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }
        if(!ValidUserAndPass()){
            Snackbar.make(findViewById(android.R.id.content),"Wrong User Name or Password",Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }

        return true
    }
    private fun ValidUserAndPass():Boolean{
        return true;
    }
}