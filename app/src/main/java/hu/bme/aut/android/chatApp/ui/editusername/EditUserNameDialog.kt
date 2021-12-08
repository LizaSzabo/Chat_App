package hu.bme.aut.android.chatApp.ui.editusername

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
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
            if (inputIsValid()) {
                viewModel.updateUserName(binding.editTextLoginName.text.toString())
                Log.i("step", "execute ended")
            }
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        return binding.root
    }

    private fun inputIsValid(): Boolean {
        if (binding.editTextLoginName.text.toString() == currentUser?.userName) {
            binding.editTextLoginName.error = getString(R.string.your_name)
            return false
        }
        if (binding.editTextLoginName.text.toString().isEmpty()) {
            binding.editTextLoginName.error = getString(R.string.user_not_empty)
            return false
        }
        return true
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is EditUserViewModel.EditCancelled -> binding.editTextLoginName.error = "User Name already exists"
        }
    }

    override fun render(viewState: EditUserNameViewState) {
        when (viewState) {
            Initial -> {
                binding.errorText.isVisible = false
            }
            EditUserNameError -> {
                binding.errorText.isVisible = true
            }
            EditUserNameSuccess -> {
                editbinding.tvUserName.text = binding.editTextLoginName.text.toString()
                dismiss()
            }
            UpdateCancelled -> {
                binding.errorText.isVisible = false
            }
        }.exhaustive
    }
}