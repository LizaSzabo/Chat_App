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
            dialog?.dismiss()
        }

        return binding.root
    }
}