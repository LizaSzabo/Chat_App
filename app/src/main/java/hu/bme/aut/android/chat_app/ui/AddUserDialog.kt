package hu.bme.aut.android.chat_app.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.databinding.DialogAddUserToConversationBinding

class AddUserDialog: DialogFragment() {

    private lateinit var binding: DialogAddUserToConversationBinding
    lateinit var listener: AddUserListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddUserToConversationBinding.inflate(inflater, container, false)
        Log.i("users", "user.userName")
        binding.btnSave.setOnClickListener {
         //   val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")

            if (validateNewUser(binding.editTextUserName.text.toString(), binding.editTextUserName)) {
                    listener.onAddUser(binding.editTextUserName.text.toString())



                dialog?.dismiss()
            }

        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }

        return binding.root
    }

     fun validateNewUser(userName: String, editText: EditText?): Boolean {
        if (userName.isEmpty()) {
            editText?.error = "User Name cannot be empty"
            return false
        }

        for(user in usersList){
            if(user.userName == userName ) {
                for(conversation in user.conversations!!) {
                    if (conversation.code == currentConversation?.code) {
                        editText?.error = "User already added"
                        return false
                    }
                }
                return true
            }
        }
         editText?.error = "User doesn't exist"
        return false
    }

    interface AddUserListener{
        fun onAddUser(userName: String)
    }
}