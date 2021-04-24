package hu.bme.aut.android.chat_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.User
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.DialogEditConversationBinding

class EditConversationDialog(var pos: Int) : DialogFragment() {
    private lateinit var binding: DialogEditConversationBinding
     lateinit var listener: EditConversationListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditConversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{
            if(binding.editTextConversationTitle.text.toString().isEmpty()){
                binding.editTextConversationTitle.error = getString(R.string.title_not_empty)
            }else {
                currentConversation?.let { it1 ->
                    currentConversation?.picture?.let { it2 ->
                        currentConversation?.favourite?.let { it3 ->
                            currentConversation?.id?.let { it4 ->
                                currentConversation?.code?.let { it5 ->
                                    Conversation(
                                        it4, binding.editTextConversationTitle.text.toString(),
                                        "", it1?.messages, it2, it3, it5
                                    )
                                }
                            }
                        }
                    }
                }?.let { it2 ->
                    listener.onConversationTitleChange(
                        it2, pos
                    )
                }



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