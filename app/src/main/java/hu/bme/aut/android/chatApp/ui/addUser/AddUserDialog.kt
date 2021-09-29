package hu.bme.aut.android.chatApp.ui.addUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.usersList
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.DialogAddUserToConversationBinding


class AddUserDialog : RainbowCakeDialogFragment<AddUserViewState, AddUserViewModel>() {

    override fun getViewResource() = R.layout.dialog_add_user_to_conversation
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var binding: DialogAddUserToConversationBinding
    lateinit var listener: AddUserListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddUserToConversationBinding.inflate(inflater, container, false)
        Log.i("users", "user.userName")
        binding.btnSave.setOnClickListener {
            //   val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")

            if (currentConversation?.code?.let { it1 ->
                    validateNewUser(binding.editTextUserName.text.toString(), binding.editTextUserName, it1)
                } == true) {
                listener.onAddUser(binding.editTextUserName.text.toString())



                dialog?.dismiss()
            }

        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        return binding.root
    }

    fun validateNewUser(userName: String, editText: EditText?, code: String): Boolean {
        if (userName.isEmpty()) {
            editText?.error = "User Name cannot be empty"
            return false
        }

        for (user in usersList) {
            if (user.userName == userName) {
                for (conversation in user.conversations!!) {
                    if (conversation.code == code) {
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

    interface AddUserListener {
        fun onAddUser(userName: String)
    }

    override fun render(viewState: AddUserViewState) {
        when (viewState) {
            Initial -> Unit
        }.exhaustive
    }
}