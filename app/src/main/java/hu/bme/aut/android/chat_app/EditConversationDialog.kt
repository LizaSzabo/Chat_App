package hu.bme.aut.android.chat_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.databinding.DialogEditConversationBinding
import hu.bme.aut.android.chat_app.databinding.DialogEditUserNameBinding

class EditConversationDialog(var pos: Int) : DialogFragment() {
    private lateinit var binding: DialogEditConversationBinding
     lateinit var listener: EditConversationListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditConversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{
            if(binding.editTextConversationTitle.text.toString().isEmpty()){
                binding.editTextConversationTitle.error = getString(R.string.title_not_empty)
            }else {
                //  currentConversation?.name = binding.editTextConversationTitle.text.toString()
                currentConversation?.let { it1 ->
                    currentConversation?.picture?.let { it2 ->
                        currentConversation?.favourite?.let { it3 ->
                            Conversation(
                                binding.editTextConversationTitle.text.toString(),
                                "", it1?.messages, it2, it3
                            )
                        }
                    }
                }?.let { it2 ->
                    listener.onConversationTitleChange(
                        it2, pos
                    )
                }

             //   currentConversation?.name?.let { it1 -> Log.i("aaa", it1) }
                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }

    interface EditConversationListener {
        fun onConversationTitleChange(conversation: Conversation, pos: Int)
    }
}