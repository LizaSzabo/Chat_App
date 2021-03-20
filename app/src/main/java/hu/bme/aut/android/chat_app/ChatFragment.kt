package hu.bme.aut.android.chat_app

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.chat_app.Adapter_Rv.ChatAdapter
import hu.bme.aut.android.chat_app.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chat_app.databinding.FragmentChatBinding
import hu.bme.aut.android.chat_app.databinding.FragmentMessagesBinding


class ChatFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentChatBinding
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentChatBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatButtomToolbar)
        setHasOptionsMenu(true)
       // fragmentBinding.chatTopToolbar.inflateMenu(R.menu.chat_menu)
        fragmentBinding.chatButtomToolbar.title = ""
        fragmentBinding.ibSend.setOnClickListener{
            if( fragmentBinding.text.text.toString().isNotEmpty()) {
                var message = hu.bme.aut.android.chat_app.Model.Message(
                    "first",
                    "second",
                    fragmentBinding.text.text.toString()
                )
                chatAdapter.addMessage(message)
                fragmentBinding.text.setText("")
            }
        }

        initRecyclerView()

        return fragmentBinding.root
    }
    private fun initRecyclerView(){
        chatAdapter = ChatAdapter()
        fragmentBinding.rvChat.layoutManager = LinearLayoutManager( context)
        fragmentBinding.rvChat.adapter =  chatAdapter
       // chatAdapter.itemClickListener = this
        chatAdapter.addAll()
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