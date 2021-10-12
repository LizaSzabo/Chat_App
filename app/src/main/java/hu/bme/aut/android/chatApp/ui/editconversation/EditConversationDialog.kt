package hu.bme.aut.android.chatApp.ui.editconversation

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.DialogEditConversationBinding

class EditConversationDialog(private var pos: Int, private val conversation: Conversation) :
    RainbowCakeDialogFragment<EditConversationViewState, EditConversationViewModel>() {

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
                viewModel.updateConversationName(conversation, binding.editTextConversationTitle.text.toString())
            }
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        return binding.root
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is EditConversationViewModel.ConversationUpdated -> {
                val updatedConversation = conversation.copy(name = binding.editTextConversationTitle.text.toString())
                listener.onConversationTitleChange(
                    updatedConversation, pos
                )

                dialog?.dismiss()
            }
        }
    }

    interface EditConversationListener {
        fun onConversationTitleChange(conversation: Conversation, pos: Int)
    }

    override fun render(viewState: EditConversationViewState) {
        when (viewState) {
            Initial -> {
                binding.errorText.isVisible = false
            }
            UpdateError -> {
                binding.errorText.isVisible = true
            }
            UpdateSucceeded -> binding.errorText.isVisible = false
        }.exhaustive
    }
}