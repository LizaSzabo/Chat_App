package hu.bme.aut.android.chat_app.ui

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.datastore.generated.model.User.*
import hu.bme.aut.android.chat_app.ChatApplication.Companion.convid
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.DialogAddconversationBinding
import java.io.ByteArrayOutputStream

class AddConversationDialog: DialogFragment() {
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
                    listener.onAddConversation(Conversation("0", binding.editTextConversationTitle.text.toString(),
                        binding.editTextTypeTitle.text.toString(), defaultMessages,  b, false))

                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }


    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
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