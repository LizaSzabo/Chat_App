package hu.bme.aut.android.chat_app.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.Network.updateUserPassword
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.DialogChangePasswordBinding

class ChangePassDialog : DialogFragment() {
    private lateinit var binding: DialogChangePasswordBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogChangePasswordBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{
            if(validateInput(binding.editActualPass,
                    binding.editNewPass,
                    binding.editConfirmPass,
                    binding.editActualPass.text.toString(),
                    binding.editNewPass.text.toString(),
                    binding.editConfirmPass.text.toString())) {
                val user = currentUser
                currentUser = currentUser?.let { it1 ->
                    currentUser?.profilePicture?.let { it2 ->
                        currentUser?.userName?.let { it3 ->
                            User(
                                 it3, binding.editNewPass.text.toString(),
                                it2, it1.conversations )
                        }
                    }
                }
                usersList.find { it == user }?.password = currentUser?.password.toString()

                updateUserPassword(currentUser?.password.toString())

                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    fun validateInput(actualPass: EditText?, newPass: EditText?, confirmedPass: EditText?, actualPassText: String, editPassText: String, confirmedPassText: String): Boolean {
        if(actualPassText.isEmpty()){
            actualPass?.error = getString(R.string.wrong_pass)
           // binding.editActualPass.backgroundTintList = (ColorStateList.valueOf(Color.parseColor("#ff0000")))
            return false
        }
        else if(editPassText.isEmpty()){
            newPass?.error = getString(R.string.pass_required)
            return false
        }
        else if(confirmedPassText.isEmpty()){
            confirmedPass?.error = getString(R.string.pass_confirmation_failed)
            return false
        }
        else if(confirmedPassText != editPassText){
            confirmedPass?.error = getString(R.string.pass_confirmation_failed)
            return false
        }
        return true
    }
}