package hu.bme.aut.android.chatApp.ui.addUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.DialogAddUserToConversationBinding


class AddUserDialog(private val conversation: Conversation) : RainbowCakeDialogFragment<AddUserViewState, AddUserViewModel>() {

    override fun getViewResource() = R.layout.dialog_add_user_to_conversation
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var binding: DialogAddUserToConversationBinding
    lateinit var listener: AddUserListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddUserToConversationBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener { viewModel.addUserToConversation(binding.editTextUserName.text.toString(), conversation) }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        return binding.root
    }

    fun validateNewUser(userName: String, editText: EditText?, code: String): Boolean {
        if (userName.isEmpty()) {
            editText?.error = "User Name cannot be empty"
            return false
        }

        editText?.error = "User doesn't exist"
        return false
    }

    interface AddUserListener {
        fun onAddUser(userName: String)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            AddUserViewModel.AddUserCancel -> binding.etUserName.error = "User doesn't exist"
            AddUserViewModel.AddUserAlreadyAdded -> binding.etUserName.error = "User already in conversation"
        }
    }

    override fun render(viewState: AddUserViewState) {
        when (viewState) {
            Initial -> Unit
            UserAddedError -> Unit
            UserAddedSuccess -> {
                dialog?.dismiss()
            }
            UserAddedCancel -> Unit
        }.exhaustive
    }
}