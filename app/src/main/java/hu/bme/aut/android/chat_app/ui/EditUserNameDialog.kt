package hu.bme.aut.android.chat_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.datastore.generated.model.User.USER_NAME
import hu.bme.aut.android.chat_app.ChatApplication
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Network.updateUserName
import hu.bme.aut.android.chat_app.Network.updateUserPicture
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.DialogEditUserNameBinding
import hu.bme.aut.android.chat_app.databinding.FragmentEditProfileBinding

class EditUserNameDialog(var editbinding: FragmentEditProfileBinding): DialogFragment() {
    private lateinit var binding: DialogEditUserNameBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditUserNameBinding.inflate(inflater, container, false)


        binding.btnSave.setOnClickListener{
            var ok = true
            for(user in usersList){
                if(user.userName == binding.editTextLoginName.text.toString()){
                    binding.editTextLoginName.error = "User Name already exists"
                    ok = false
                }
            }
            if(binding.editTextLoginName.text.toString().isEmpty()){
                binding.editTextLoginName.error = getString(R.string.user_not_empty)
                ok = false
            }
            if(ok) {
                val user = currentUser
                val name = user?.userName
                currentUser?.userName = binding.editTextLoginName.text.toString()
                usersList.find { it == user }?.userName = currentUser?.userName.toString()
                if(user?.conversations != null) {
                    for (conv in user?.conversations!!) {
                        for (message in conv.messages!!) {
                            if (message.sender == name) {
                                message.sender = binding.editTextLoginName.text.toString()
                            }
                        }
                    }
                }
                editbinding.tvUserName.text = ChatApplication.currentUser?.userName

                updateUserName(binding.editTextLoginName.text.toString(), name)

                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }
}