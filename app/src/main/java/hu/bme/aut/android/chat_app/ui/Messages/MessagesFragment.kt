package hu.bme.aut.android.chat_app.ui.Messages

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chat_app.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chat_app.ChangeConversationPictureDialog
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.EditConversationDialog
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentMessagesBinding


class MessagesFragment : RainbowCakeFragment<MessagesViewState, MessagesViewModel>(), ConversationsAdapter.ConversationItemClickListener, EditConversationDialog.EditConversationListener,
    ChangeConversationPictureDialog.ChangeConversationPictureListener {

    private lateinit var fragmentBinding: FragmentMessagesBinding
    private lateinit var conversationsAdapter: ConversationsAdapter
    private lateinit  var myContext: FragmentActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMessagesBinding.bind(view)
        fragmentBinding= binding

        // Inflate the layout for this fragment
        fragmentBinding.imageButtonWrite.setOnClickListener(View.OnClickListener { viewModel.openChatActivity(findNavController()) })
        fragmentBinding.imageButtonProfile.setOnClickListener(View.OnClickListener { viewModel.openEditProfileActivity(findNavController()) })

        //fragmentBinding.chatListToolbar.inflateMenu(R.menu.messages_toolbar_menu);

        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatListToolbar)
        setHasOptionsMenu(true)
        fragmentBinding.chatListToolbar.title = ""
        fragmentBinding.txtTitle.text = currentUser?.userName

        fragmentBinding.ibSearch.setOnClickListener{
            conversationsAdapter.addAll(fragmentBinding.editTextSearch.text.toString())
        }

        fragmentBinding.editTextSearch.doOnTextChanged { _, _, _, _ -> conversationsAdapter.addAll(fragmentBinding.editTextSearch.text.toString())  }
        fragmentBinding.imageButtonProfile.setImageURI(currentUser?.profilePicture)
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



    override fun onItemClick(conversation: Conversation) {
        currentConversation = conversation
        val action = MessagesFragmentDirections.actionMessagesFragmentToChatFragment()
        findNavController().navigate(action)
    }

    @SuppressLint("ResourceAsColor")
    override fun onItemLongClick(position: Int, view: View, conversation: Conversation): Boolean {
        currentConversation = conversation
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.menu_conversation)
        popup.setOnDismissListener(){
            view.setBackgroundColor(0)
        }
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
               R.id.delete -> conversationsAdapter.deleteConversation(position)
               R.id.edit -> {
                   val conversationDialog = EditConversationDialog(position)
                   conversationDialog.listener = this
                   conversationDialog.show(parentFragmentManager, "")
               }
                R.id.editPicture ->{
                    val changeConversationPictureDialog = ChangeConversationPictureDialog(position)
                    changeConversationPictureDialog.listener = this
                    changeConversationPictureDialog.show(parentFragmentManager, "")
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

    override fun onConversationTitleChange(conversation: Conversation, pos: Int) {Log.i("aaa","aaa")
        conversationsAdapter.UpdateConversation(conversation, pos)
    }

    override fun onConversationPictureChange(conversation: Conversation, pos: Int) {
        conversationsAdapter.UpdateConversationPicture(conversation, pos)
    }
}