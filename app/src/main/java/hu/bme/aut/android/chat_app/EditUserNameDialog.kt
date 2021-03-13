package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.DialogCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import hu.bme.aut.android.chat_app.databinding.DialogEditUserNameBinding

class EditUserNameDialog: DialogFragment() {
    private lateinit var binding: DialogEditUserNameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditUserNameBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{
            if(binding.editTextLoginName.text.toString().isEmpty()){
                binding.editTextLoginName.error = getString(R.string.user_not_empty)
            }else
                 dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }
}