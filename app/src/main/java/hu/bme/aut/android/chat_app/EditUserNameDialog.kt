package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.DialogCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.databinding.DialogEditUserNameBinding

class EditUserNameDialog(): DialogFragment() {
    private lateinit var binding: DialogEditUserNameBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditUserNameBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{
            if(binding.editTextLoginName.text.toString().isEmpty()){
                binding.editTextLoginName.error = getString(R.string.user_not_empty)
            }else {
                val user = currentUser
                val name = user?.userName
                currentUser?.userName = binding.editTextLoginName.text.toString()
                usersList.find { it == user }?.userName = currentUser?.userName.toString()
                for(conv in user?.conversations!!) {
                    for(message in conv.messages){
                        if(message.sender == name){
                            message.sender = binding.editTextLoginName.text.toString()
                        }
                    }
                }
                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }


}