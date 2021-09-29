package hu.bme.aut.android.chatApp.ui.editusername

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.ChatApplication.Companion.usersList
import hu.bme.aut.android.chatApp.Network.updateUserName
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.DialogEditUserNameBinding
import hu.bme.aut.android.chatApp.databinding.FragmentEditProfileBinding

class EditUserNameDialog(private var editbinding: FragmentEditProfileBinding) :
    RainbowCakeDialogFragment<EditUserNameViewState, EditUserViewModel>() {

    override fun getViewResource() = R.layout.dialog_edit_user_name
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var binding: DialogEditUserNameBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditUserNameBinding.inflate(inflater, container, false)


        binding.btnSave.setOnClickListener {
            var ok = true
            for (user in usersList) {
                if (user.userName == binding.editTextLoginName.text.toString()) {
                    binding.editTextLoginName.error = "User Name already exists"
                    ok = false
                }
            }
            if (binding.editTextLoginName.text.toString().isEmpty()) {
                binding.editTextLoginName.error = getString(R.string.user_not_empty)
                ok = false
            }
            if (ok) {
                val user = currentUser
                val name = user?.userName
                currentUser?.userName = binding.editTextLoginName.text.toString()
                usersList.find { it == user }?.userName = currentUser?.userName.toString()
                if (user?.conversations != null) {
                    for (conv in user.conversations!!) {
                        for (message in conv.messages!!) {
                            if (message.sender == name) {
                                message.sender = binding.editTextLoginName.text.toString()
                            }
                        }
                    }
                }
                editbinding.tvUserName.text = currentUser?.userName

                updateUserName(binding.editTextLoginName.text.toString(), name)

                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        return binding.root
    }

    override fun render(viewState: EditUserNameViewState) {
        when (viewState) {
            Initial -> Unit
        }.exhaustive
    }
}