package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.databinding.DialogAddconversationBinding
import hu.bme.aut.android.chat_app.databinding.DialogEditConversationBinding

class AddConversationDialog: DialogFragment() {
    private lateinit var binding: DialogAddconversationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddconversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            if (binding.editTextConversationTitle.text.toString().isEmpty()) {
                binding.editTextConversationTitle.error = getString(R.string.title_not_empty)

            }
            else{

            }
        }
        return binding.root
    }
}