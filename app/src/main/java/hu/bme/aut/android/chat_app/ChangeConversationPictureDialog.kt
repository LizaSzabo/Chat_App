package hu.bme.aut.android.chat_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.databinding.DialogChangeconversationpictureBinding
import hu.bme.aut.android.chat_app.databinding.DialogEditConversationBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chat_app.ChatApplication
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentRegisterBinding

class ChangeConversationPictureDialog(var pos: Int): DialogFragment() {
    private lateinit var binding: DialogChangeconversationpictureBinding
    private val PICK_IMAGE = 1
    lateinit var listener: ChangeConversationPictureDialog.ChangeConversationPictureListener
     var uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  DialogChangeconversationpictureBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{

            currentConversation?.name?.let { it1 ->
                currentConversation?.type?.let { it2 ->
                    currentConversation?.messages?.let { it3 ->
                        Conversation(
                            it1,
                            it2,
                            it3, uri )
                    }
                }
            }?.let { it2 -> listener.onConversationPictureChange(it2, pos) }
          //  currentConversation?.name?.let { it1 -> Log.i("aaa", currentConversation?.name!! ) }
            dialog?.dismiss()

        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }

        binding.ivAddPicture.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
            binding.ivAddPicture.setImageURI(intent.data)

        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            val selectedImageUri: Uri? = data?.data
            /* val imageBitmap = data?.extras?.get("data") as? Bitmap ?: return
             fragmentBinding.imageButtonEditProfile.setImageBitmap(imageBitmap)*/
            if (null != selectedImageUri) {
                // update the preview image in the layout
               binding.ivAddPicture.setImageURI(selectedImageUri)
                currentConversation?.picture = selectedImageUri
                uri = selectedImageUri
                
            }
        }
    }

    interface ChangeConversationPictureListener {
        fun onConversationPictureChange(conversation: Conversation, pos: Int)
    }

}