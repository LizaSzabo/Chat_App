package hu.bme.aut.android.chat_app

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.ChatApplication.Companion.convid
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.databinding.DialogAddconversationBinding
import kotlinx.android.synthetic.main.fragment_chat.view.*

class AddConversationDialog: DialogFragment() {
    private lateinit var binding: DialogAddconversationBinding
    lateinit var listener: AddConversationListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddconversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")

            if (validateNewConversation()) {
               val defaultMessages = mutableListOf<Message>()
                convid++
                    listener.onAddConversation(Conversation(convid, binding.editTextConversationTitle.text.toString(),
                        binding.editTextTypeTitle.text.toString(), defaultMessages,  uri, false))
                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }

    private fun validateNewConversation(): Boolean{
        if (binding.editTextConversationTitle.text.toString().isEmpty()) {
            binding.editTextConversationTitle.error = getString(R.string.title_not_empty)
            return false
        }
        if( binding.editTextTypeTitle.text.toString().isEmpty()){
            binding.editTextTypeTitle.error = "conversation type cannot be empty"
            return false
        }
       /* if(!validUserAndPass()){
            Snackbar.make(
                fragmentBinding.root, context.getString(R.string.wrong_input),
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(Color.RED)
                .show()
            return false
        }*/
        return true
    }

    interface AddConversationListener{
        fun onAddConversation(conversation: Conversation)
    }
}