package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.databinding.DialogChangeconversationpictureBinding
import hu.bme.aut.android.chat_app.databinding.DialogEditConversationBinding

class ChangeConversationPictureDialog: DialogFragment() {
    private lateinit var binding: DialogChangeconversationpictureBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  DialogChangeconversationpictureBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{

                dialog?.dismiss()
            
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }
}