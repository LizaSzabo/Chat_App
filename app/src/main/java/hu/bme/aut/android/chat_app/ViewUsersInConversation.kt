package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import hu.bme.aut.android.chat_app.databinding.FragmentChatBinding
import hu.bme.aut.android.chat_app.databinding.FragmentViewUsersBinding

class ViewUsersInConversation: Fragment() {

    private lateinit var fragmentBinding: FragmentViewUsersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentViewUsersBinding.inflate(layoutInflater)
        return fragmentBinding.root
    }

}