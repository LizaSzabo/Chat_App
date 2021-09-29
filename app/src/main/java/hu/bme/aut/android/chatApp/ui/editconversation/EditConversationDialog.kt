package hu.bme.aut.android.chatApp.ui.editconversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.DialogEditConversationBinding

class EditConversationDialog(private var pos: Int) : RainbowCakeDialogFragment<EditConversationViewState, EditConversationViewModel>() {

    override fun getViewResource() = R.layout.dialog_edit_conversation
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var binding: DialogEditConversationBinding
    lateinit var listener: EditConversationListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditConversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            if (binding.editTextConversationTitle.text.toString().isEmpty()) {
                binding.editTextConversationTitle.error = getString(R.string.title_not_empty)
            } else {
                currentConversation?.let { it1 ->
                    currentConversation?.picture?.let { it2 ->
                        currentConversation?.favourite?.let { it3 ->
                            currentConversation?.id?.let { it4 ->
                                currentConversation?.code?.let { it5 ->
                                    Conversation(
                                        it4, binding.editTextConversationTitle.text.toString(),
                                        "", it1.messages, it2, it3, it5
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

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        return binding.root
    }

    interface EditConversationListener {
        fun onConversationTitleChange(conversation: Conversation, pos: Int)
    }

    override fun render(viewState: EditConversationViewState) {
        when (viewState) {
            Initial -> Unit
        }.exhaustive
    }
}