package hu.bme.aut.android.chatApp.ui.addConversation

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Model.Message
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.DialogAddconversationBinding
import java.util.*

class AddConversationDialog : RainbowCakeDialogFragment<AddConversationViewState, AddConversationViewModel>(),
    AdapterView.OnItemSelectedListener {

    override fun getViewResource() = R.layout.dialog_addconversation
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var binding: DialogAddconversationBinding
    lateinit var listener: AddConversationListener
    private var conversationExists = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddconversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {

            val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")
            val b: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
            if (validateNewConversation()) {
                viewModel.addConversation(
                    binding.editTextConversationTitle.text.toString(),
                    binding.editTextTypeTitle.text.toString(),
                    b,
                    binding.editTextCode.text.toString()
                )

            }
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        val spinner: Spinner = binding.spinnerCategory
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.types,
                android.R.layout.simple_spinner_item
            )
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
        }
        spinner.onItemSelectedListener = this
        spinner.dropDownHorizontalOffset = -20
        return binding.root
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is AddConversationViewModel.ConversationAdded -> {
                val defaultMessages = mutableListOf<Message>()
                val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")
                val b: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                listener.onAddConversation(
                    Conversation(
                        UUID.randomUUID().toString(), binding.editTextConversationTitle.text.toString(),
                        binding.editTextTypeTitle.text.toString(), defaultMessages, b, false, binding.editTextCode.text.toString()
                    )
                )
                dismiss()
            }
            is AddConversationViewModel.ConversationCancelled ->{
                binding.editTextCode.error = "code already exists"
            }
        }
    }


    private fun validateNewConversation(): Boolean {
        if (!validateInputText(binding.editTextConversationTitle.text.toString())) {
            binding.editTextConversationTitle.error = getString(R.string.title_not_empty)
            return false
        }
        if (!validateInputText(binding.editTextTypeTitle.text.toString())) {
            binding.editTextTypeTitle.error = "conversation type cannot be empty"
            return false
        }

        // viewModel.existsConversation(binding.editTextCode.text.toString())

        return true
    }

    fun validateInputText(input: String): Boolean {
        if (input.isEmpty()) return false
        return true
    }

   interface AddConversationListener {
        fun onAddConversation(conversation: Conversation)
   }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> setSelectedItem("Private")
            1 -> setSelectedItem("Group")
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun setSelectedItem(categorySelected: String) {
        binding.editTextTypeTitle.setText(categorySelected)
    }

    override fun render(viewState: AddConversationViewState) {
        when (viewState) {
            Initial -> Unit
            ConversationAddCancel -> {
                binding.editTextCode.error = "code already exists"
            }
            ConversationAddError -> {
                Toast.makeText(context, "Conversation Add Failed", Toast.LENGTH_LONG).show()
            }
            ConversationAddSuccess -> {
                Toast.makeText(context, "Conversation Add Succeeded", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }.exhaustive
    }

}