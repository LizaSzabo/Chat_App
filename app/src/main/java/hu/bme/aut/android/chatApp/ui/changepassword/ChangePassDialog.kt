package hu.bme.aut.android.chatApp.ui.changepassword

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.DialogChangePasswordBinding

class ChangePassDialog : RainbowCakeDialogFragment<ChangePasswordViewState, ChangePasswordViewModel>() {
    private lateinit var binding: DialogChangePasswordBinding

    override fun getViewResource() = R.layout.dialog_change_password
    override fun provideViewModel() = getViewModelFromFactory()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogChangePasswordBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            if (validateInput(
                    binding.editActualPass,
                    binding.editNewPass,
                    binding.editConfirmPass,
                    binding.editActualPass.text.toString(),
                    binding.editNewPass.text.toString(),
                    binding.editConfirmPass.text.toString()
                )
            ) {
                viewModel.changeUserPassword(binding.editNewPass.text.toString())
            }
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    fun validateInput(
        actualPass: EditText?,
        newPass: EditText?,
        confirmedPass: EditText?,
        actualPassText: String,
        editPassText: String,
        confirmedPassText: String
    ): Boolean {
        when {
            actualPassText.isEmpty() -> {
                actualPass?.error = getString(R.string.wrong_pass)
                return false
            }
            editPassText.isEmpty() -> {
                newPass?.error = getString(R.string.pass_required)
                return false
            }
            confirmedPassText.isEmpty() -> {
                confirmedPass?.error = getString(R.string.pass_confirmation_failed)
                return false
            }
            confirmedPassText != editPassText -> {
                confirmedPass?.error = getString(R.string.pass_confirmation_failed)
                return false
            }
            else -> return true
        }
    }


    override fun render(viewState: ChangePasswordViewState) {
        when (viewState) {
            Initial -> {
                binding.errorText.isVisible = false
            }
            ChangeError -> {
                binding.errorText.isVisible = true
            }
            ChangeSuccess -> {
                dialog?.dismiss()
            }
        }.exhaustive
    }
}