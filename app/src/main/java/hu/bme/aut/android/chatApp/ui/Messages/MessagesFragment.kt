package hu.bme.aut.android.chatApp.ui.Messages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.Adapter_Rv.ConversationsAdapter
import hu.bme.aut.android.chatApp.ChatApplication
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentMessagesBinding
import hu.bme.aut.android.chatApp.ui.addUser.AddUserDialog
import hu.bme.aut.android.chatApp.ui.editconversation.EditConversationDialog

class MessagesFragment : RainbowCakeFragment<MessagesViewState, MessagesViewModel>(), ConversationsAdapter.ConversationItemClickListener,
    EditConversationDialog.EditConversationListener, AddUserDialog.AddUserListener {

    override fun getViewResource() = R.layout.fragment_messages
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var fragmentBinding: FragmentMessagesBinding
    private lateinit var conversationsAdapter: ConversationsAdapter
    private val pickImage = 1
    private var positionOfSelectedImage: Int = -1

    var uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/default_profilepic")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMessagesBinding.bind(view)
        fragmentBinding = binding

        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatListToolbar)
        setHasOptionsMenu(true)

        fragmentBinding.imageButtonWrite.setOnClickListener {
            viewModel.openAddConversationDialog(parentFragmentManager, conversationsAdapter)
        }
        fragmentBinding.imageButtonWrite.isVisible = false
        fragmentBinding.imageButtonProfile.setOnClickListener { viewModel.openEditProfileActivity(findNavController()) }
        fragmentBinding.chatListToolbar.title = ""
        fragmentBinding.txtTitle.text = currentUser?.userName
        fragmentBinding.ibSearch.setOnClickListener {
            conversationsAdapter.addAll(fragmentBinding.editTextSearch.text.toString())
        }
        fragmentBinding.editTextSearch.doOnTextChanged { _, _, _, _ -> conversationsAdapter.addAll(fragmentBinding.editTextSearch.text.toString()) }

        var resized: Bitmap? = null
        resized = if (currentUser?.profilePicture?.height!! > currentUser?.profilePicture?.width!!) {
            currentUser?.profilePicture?.resizeByWidth(fragmentBinding.imageButtonProfile.layoutParams.width)
        } else {
            currentUser?.profilePicture?.resizeByHeight(fragmentBinding.imageButtonProfile.layoutParams.height)

        }
        fragmentBinding.imageButtonProfile.setImageBitmap(resized)

        initRecyclerView()
        viewModel.init(fragmentBinding.editTextSearch.text.toString(), conversationsAdapter)
    }

    private fun Bitmap.resizeByHeight(height: Int): Bitmap {
        val ratio: Float = this.height.toFloat() / this.width.toFloat()
        val width: Int = Math.round(height / ratio)

        return Bitmap.createScaledBitmap(
            this,
            width,
            height,
            false
        )
    }

    private fun Bitmap.resizeByWidth(width: Int): Bitmap {
        val ratio: Float = this.width.toFloat() / this.height.toFloat()
        val height: Int = Math.round(width / ratio)

        return Bitmap.createScaledBitmap(
            this,
            width,
            height,
            false
        )
    }

    private fun initRecyclerView() {
        conversationsAdapter = ConversationsAdapter()
        fragmentBinding.rvConversations.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvConversations.adapter = conversationsAdapter
        conversationsAdapter.itemClickListener = this
       // conversationsAdapter.addAll(fragmentBinding.editTextSearch.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.messages_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Log_out) {
            val action = MessagesFragmentDirections.actionMessagesFragmentToLoginFragment(-1)
            findNavController().navigate(action)
        } else if (item.itemId == R.id.settings) {
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
        positionOfSelectedImage = position

        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.menu_conversation)
        popup.setOnDismissListener() {
            view.setBackgroundColor(0)
        }
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> viewModel.deleteConversation(position, conversation)//conversationsAdapter.deleteConversation(position)
                R.id.edit -> {
                    val conversationDialog = EditConversationDialog(position, conversation)
                    conversationDialog.listener = this
                    conversationDialog.show(parentFragmentManager, "")
                }
                R.id.editPicture -> {
                //    val intent = Intent()
                  //  intent.type = "image/*"
                   // intent.action = Intent.ACTION_GET_CONTENT
                   // startActivityForResult(Intent.createChooser(intent, "Select Picture"), pickImage)

                    openSomeActivityForResult()
                   // conversationsAdapter.updateConversationPicture(conversation, position)

                }
                R.id.addUser -> {
                    val addUserDialog = AddUserDialog()
                    addUserDialog.listener = this
                    addUserDialog.show(parentFragmentManager, "")
                }
            }
            false
        }
        popup.show()

        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImage) {
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                val yourBitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImageUri)
                currentConversation?.picture = yourBitmap
                for (user in ChatApplication.usersList) {
                    for (conversation in user.conversations!!) {
                        if (conversation.code == currentConversation?.code) {
                            viewModel.updateUser(user)
                        }
                    }
                }


                uri = selectedImageUri
            }
            val action = MessagesFragmentDirections.actionMessagesFragmentSelf()
            findNavController().navigate(action)
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                //fragmentBinding.ivAddPicture.setImageURI(selectedImageUri)
                val conversationPicture: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImageUri)
                viewModel.updateConversationImage(currentConversation!!, conversationPicture, positionOfSelectedImage)
                uri = selectedImageUri
            }
        }
    }

    private fun openSomeActivityForResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    override fun render(viewState: MessagesViewState) {
        when (viewState) {
            Initial -> {

            }
            ConversationLoadSuccess -> {
                Toast.makeText(context, "Conversation successfully loaded", Toast.LENGTH_LONG).show()
                fragmentBinding.imageButtonWrite.isVisible = true
            }
            ConversationLoadError -> {}
            ConversationDeleteSuccess -> {
                Toast.makeText(context, "Conversation successfully deleted", Toast.LENGTH_LONG).show()
                fragmentBinding.imageButtonWrite.isVisible = true
            }
            ConversationDeleteError -> {
                Toast.makeText(context, "Conversation delete failed", Toast.LENGTH_LONG).show()
                fragmentBinding.imageButtonWrite.isVisible = false
            }
        }.exhaustive
    }

    override fun onConversationTitleChange(conversation: Conversation, pos: Int) {
        conversationsAdapter.updateConversation(conversation, pos)
    }

    override fun onAddUser(userName: String) {
        conversationsAdapter.addConversationToUser(userName)
    }

    override fun onEvent(event: OneShotEvent) {
        when(event){
            is MessagesViewModel.UpdateConversationImageError ->{
                Toast.makeText(context, "Can't save new conversation picture", Toast.LENGTH_LONG).show()
            }
            is MessagesViewModel.UpdateConversationImageSuccess ->{
              /* val action = MessagesFragmentDirections.actionMessagesFragmentSelf()
                findNavController().navigate(action)*/
            }
        }
    }
}