package hu.bme.aut.android.chat_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding
import hu.bme.aut.android.chat_app.databinding.FragmentMessagesBinding


class MessagesFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentMessagesBinding
    private lateinit  var myContext: FragmentActivity

    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentBinding =FragmentMessagesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        fragmentBinding.imageButtonWrite.setOnClickListener(View.OnClickListener { openChatActivity() })
        fragmentBinding.imageButtonProfile.setOnClickListener(View.OnClickListener { openEditProfileActivity() })
        return fragmentBinding.root
    }


private fun openChatActivity() {
    val action = MessagesFragmentDirections.actionMessagesFragmentToChatFragment()
    findNavController().navigate(action)

}

private fun openEditProfileActivity() {
    val action = MessagesFragmentDirections.actionMessagesFragmentToEditProfileFragment()
    findNavController().navigate(action)
}


}