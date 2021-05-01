package hu.bme.aut.android.chat_app.ui

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
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.ChatApplication.Companion.allConversationList
import hu.bme.aut.android.chat_app.ChatApplication.Companion.convid
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.DialogAddconversationBinding
import java.util.*

class AddConversationDialog: DialogFragment(), AdapterView.OnItemSelectedListener  {
    private lateinit var binding: DialogAddconversationBinding
    lateinit var listener: AddConversationListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddconversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")
            val b: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
            if (validateNewConversation()) {
               val defaultMessages = mutableListOf<Message>()
                convid++
                    listener.onAddConversation(Conversation(
                        UUID.randomUUID().toString(), binding.editTextConversationTitle.text.toString(),
                        binding.editTextTypeTitle.text.toString(), defaultMessages,  b, false, binding.editTextCode.text.toString()))

                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener{
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


    private fun validateNewConversation(): Boolean{
        if (validateInputText(binding.editTextConversationTitle.text.toString())) {
            binding.editTextConversationTitle.error = getString(R.string.title_not_empty)
            return false
        }
        if(validateInputText( binding.editTextTypeTitle.text.toString())){
            binding.editTextTypeTitle.error = "conversation type cannot be empty"
            return false
        }

        for(conversation in allConversationList){
            if(conversation.code == binding.editTextCode.text.toString()) {
                binding.editTextCode.error = "code already exists"
                return false
            }
        }

        return true
    }

    fun validateInputText(input: String): Boolean{
        if(input.isEmpty()) return false
         return true
    }

    interface AddConversationListener{
        fun onAddConversation(conversation: Conversation)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 -> setSelectedItem("Private")
            1 -> setSelectedItem("Group")
        }

    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun  setSelectedItem(categorySelected: String){
        binding.editTextTypeTitle.setText(categorySelected)
    }
}