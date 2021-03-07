package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.chat_app.databinding.DialogChangePasswordBinding

class ChangePassDialog : DialogFragment() {
    private lateinit var binding: DialogChangePasswordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }
}