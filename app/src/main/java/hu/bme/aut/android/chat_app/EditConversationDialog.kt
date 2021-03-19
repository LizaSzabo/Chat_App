package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.databinding.DialogEditConversationBinding
import hu.bme.aut.android.chat_app.databinding.DialogEditUserNameBinding

class EditConversationDialog : DialogFragment() {
    private lateinit var binding: DialogEditConversationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditConversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{
            if(binding.editTextConversationTitle.text.toString().isEmpty()){
                binding.editTextConversationTitle.error = getString(R.string.title_not_empty)
            }else
                dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }
}