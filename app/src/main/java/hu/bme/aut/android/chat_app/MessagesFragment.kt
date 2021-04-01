package hu.bme.aut.android.chat_app

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chat_app.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.databinding.FragmentLoginBinding
import hu.bme.aut.android.chat_app.databinding.FragmentMessagesBinding
import hu.bme.aut.android.chat_app.ui.Login.*
import hu.bme.aut.android.chat_app.ui.Messages.DataReady
import hu.bme.aut.android.chat_app.ui.Messages.Initial
import hu.bme.aut.android.chat_app.ui.Messages.Loading
import hu.bme.aut.android.chat_app.ui.Messages.MessagesViewModel
import hu.bme.aut.android.chat_app.ui.Messages.MessagesViewState


class MessagesFragment : RainbowCakeFragment<MessagesViewState, MessagesViewModel>(), ConversationsAdapter.ConversationItemClickListener {

    private lateinit var fragmentBinding: FragmentMessagesBinding
    private lateinit var conversationsAdapter: ConversationsAdapter
    private lateinit  var myContext: FragmentActivity

   /* override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMessagesBinding.bind(view)
        fragmentBinding= binding

        // Inflate the layout for this fragment
        fragmentBinding.imageButtonWrite.setOnClickListener(View.OnClickListener { openChatActivity() })
        fragmentBinding.imageButtonProfile.setOnClickListener(View.OnClickListener { openEditProfileActivity() })

        //fragmentBinding.chatListToolbar.inflateMenu(R.menu.messages_toolbar_menu);

        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatListToolbar)
        setHasOptionsMenu(true)
        fragmentBinding.chatListToolbar.title = ""
        fragmentBinding.txtTitle.text = currentUser?.userName

        fragmentBinding.ibSearch.setOnClickListener{
            conversationsAdapter.addAll(fragmentBinding.editTextSearch.text.toString())
        }
        fragmentBinding.editTextSearch.doOnTextChanged { _, _, _, _ -> conversationsAdapter.addAll(fragmentBinding.editTextSearch.text.toString())  }
        initRecyclerView()

    }

    private fun initRecyclerView(){
        Log.i("recy" ,"recy")
        conversationsAdapter = ConversationsAdapter()
        fragmentBinding.rvConversations.layoutManager = LinearLayoutManager( context)
        fragmentBinding.rvConversations.adapter = conversationsAdapter
        conversationsAdapter.itemClickListener = this
        conversationsAdapter.addAll(fragmentBinding.editTextSearch.text.toString())
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
        currentConversation = conversation
        val action = MessagesFragmentDirections.actionMessagesFragmentToChatFragment()
        findNavController().navigate(action)
    }

    @SuppressLint("ResourceAsColor")
    override fun onItemLongClick(position: Int, view: View): Boolean {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.menu_conversation)
        popup.setOnDismissListener(){
            view.setBackgroundColor(0)
        }
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
               R.id.delete -> conversationsAdapter.deleteConversation(position)
               R.id.edit -> {
                   val conversationDialog = EditConversationDialog()
                   conversationDialog.show(parentFragmentManager, "")
               }
            }
            false
        }
        popup.show()
        return false
    }


    override fun getViewResource() = R.layout.fragment_messages

    override fun provideViewModel()= getViewModelFromFactory()

    override fun render(viewState: MessagesViewState) {
        when(viewState){
            Initial -> {

            }
            Loading ->{

            }
            DataReady -> {

            }
            else ->{}
        }.exhaustive
    }
}