package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.chat_app.databinding.FragmentChatBinding
import hu.bme.aut.android.chat_app.databinding.FragmentMessagesBinding


class ChatFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentChatBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentChatBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatButtomToolbar)
        setHasOptionsMenu(true)
       // fragmentBinding.chatTopToolbar.inflateMenu(R.menu.chat_menu)
        fragmentBinding.chatButtomToolbar.title = ""
        return fragmentBinding.root
    }

    override  fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.i("log out", "log out")
        inflater.inflate(R.menu.chat_menu, menu)
        super.onCreateOptionsMenu(menu!!, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      /*  if (item.itemId == R.id.Log_out) {
            val action = MessagesFragmentDirections.actionMessagesFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        else if(item.itemId == R.id.settings){
            val action = MessagesFragmentDirections.actionMessagesFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }*/
        return super.onOptionsItemSelected(item)
    }

}