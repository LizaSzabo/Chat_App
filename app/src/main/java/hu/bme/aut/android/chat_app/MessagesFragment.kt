package hu.bme.aut.android.chat_app

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.chat_app.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.databinding.FragmentMessagesBinding


class MessagesFragment : Fragment(), ConversationsAdapter.ConversationItemClickListener {

    private lateinit var fragmentBinding: FragmentMessagesBinding
    private lateinit  var myContext: FragmentActivity

   /* override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBinding =FragmentMessagesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        fragmentBinding.imageButtonWrite.setOnClickListener(View.OnClickListener { openChatActivity() })
        fragmentBinding.imageButtonProfile.setOnClickListener(View.OnClickListener { openEditProfileActivity() })

        //fragmentBinding.chatListToolbar.inflateMenu(R.menu.messages_toolbar_menu);

        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatListToolbar)
        setHasOptionsMenu(true)
        fragmentBinding.chatListToolbar.title = ""

        initRecyclerView()

        return fragmentBinding.root
    }

    private fun initRecyclerView(){
        Log.i("recy" ,"recy")
        val conversationsAdapter = ConversationsAdapter()
        fragmentBinding.rvConversations.layoutManager = LinearLayoutManager( context)
        fragmentBinding.rvConversations.adapter = conversationsAdapter
        conversationsAdapter.itemClickListener = this
    }

   override  fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       Log.i("log out", "log out")
        inflater.inflate(R.menu.messages_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu!!, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Log_out) {
            val action = MessagesFragmentDirections.actionMessagesFragmentToLoginFragment(-1)
            findNavController().navigate(action)
        }
        else if(item.itemId == R.id.settings){
            val action = MessagesFragmentDirections.actionMessagesFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }


private fun openChatActivity() {
    val action = MessagesFragmentDirections.actionMessagesFragmentToChatFragment()
    findNavController().navigate(action)

}

private fun openEditProfileActivity() {
    val action = MessagesFragmentDirections.actionMessagesFragmentToEditProfileFragment()
    findNavController().navigate(action)
}

    override fun onItemClick(conversation: Conversation) {
        TODO("Not yet implemented")
    }


}