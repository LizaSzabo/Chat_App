package hu.bme.aut.android.chatApp.ui.Messages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentMessagesBinding
import hu.bme.aut.android.chatApp.extensions.resizeByHeight
import hu.bme.aut.android.chatApp.extensions.resizeByWidth
import hu.bme.aut.android.chatApp.ui.addUser.AddUserDialog
import hu.bme.aut.android.chatApp.ui.editconversation.EditConversationDialog
import kotlin.math.roundToInt

class MessagesFragment : RainbowCakeFragment<MessagesViewState, MessagesViewModel>(), ConversationsAdapter.ConversationItemClickListener,
    EditConversationDialog.EditConversationListener {

    override fun getViewResource() = R.layout.fragment_messages
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var fragmentBinding: FragmentMessagesBinding
    private lateinit var conversationsAdapter: ConversationsAdapter
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
             viewModel.init(fragmentBinding.editTextSearch.text.toString(), conversationsAdapter)
        }
        fragmentBinding.editTextSearch.doOnTextChanged { _, _, _, _ -> viewModel.init(fragmentBinding.editTextSearch.text.toString(), conversationsAdapter) }

        var resized: Bitmap? = null
        resized = if (currentUser?.profilePicture?.height!! > currentUser?.profilePicture?.width!!) {
            currentUser?.profilePicture?.resizeByWidth(fragmentBinding.imageButtonProfile.layoutParams.width)
        } else {
            currentUser?.profilePicture?.resizeByHeight(fragmentBinding.imageButtonProfile.layoutParams.height)

        }
        fragmentBinding.imageButtonProfile.setImageBitmap(resized)

        initRecyclerView()
    }


    private fun initRecyclerView() {
        conversationsAdapter = ConversationsAdapter()
        fragmentBinding.rvConversations.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvConversations.adapter = conversationsAdapter
        conversationsAdapter.itemClickListener = this
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
        Log.i("navigation", conversation.id)
        val action = MessagesFragmentDirections.actionMessagesFragmentToChatFragment(conversation.id)
        findNavController().navigate(action)
    }

    @RequiresApi(Build.VERSION_CODES.P)
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
                    openSomeActivityForResult()
                }
                R.id.addUser -> {
                    val addUserDialog = AddUserDialog(conversation)
                    addUserDialog.show(parentFragmentManager, "")
                }
            }
            false
        }
        popup.show()

        return false
    }

    override fun onItemStartClick(conversation: Conversation, favourite: Boolean) {
        viewModel.updateConversationFavourite(conversation, favourite)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                val conversationPicture: Bitmap = getCapturedImage(selectedImageUri)
                viewModel.updateConversationImage(currentConversation!!, conversationPicture, positionOfSelectedImage)
                uri = selectedImageUri
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun openSomeActivityForResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    override fun render(viewState: MessagesViewState) {
        when (viewState) {
            Initial -> {
                viewModel.init(fragmentBinding.editTextSearch.text.toString(), conversationsAdapter)
            }
            ConversationLoadSuccess -> {
                Toast.makeText(context, "Conversation successfully loaded", Toast.LENGTH_LONG).show()
                fragmentBinding.imageButtonWrite.isVisible = true
            }
            ConversationLoadError -> {

            }
            ConversationDeleteSuccess -> {
                Toast.makeText(context, "Conversation successfully deleted", Toast.LENGTH_LONG).show()
                fragmentBinding.imageButtonWrite.isVisible = true
            }
            ConversationDeleteError -> {
                Toast.makeText(context, "Conversation delete failed", Toast.LENGTH_LONG).show()
                fragmentBinding.imageButtonWrite.isVisible = false
            }
            ConversationFavouriteUpdateError -> {
                Toast.makeText(context, "Conversation favourite failed", Toast.LENGTH_LONG).show()
            }
            ConversationFavouriteUpdateSuccess -> {
            }
        }.exhaustive
    }

    override fun onConversationTitleChange(conversation: Conversation, pos: Int) {
        conversationsAdapter.updateConversation(conversation, pos)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is MessagesViewModel.UpdateConversationImageError -> {
                Toast.makeText(context, "Can't save new conversation picture", Toast.LENGTH_LONG).show()
            }
            is MessagesViewModel.UpdateConversationImageSuccess -> {
            }
        }
    }

    override fun onPause() {
        fragmentBinding.editTextSearch.setText("")
        super.onPause()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getCapturedImage(selectedPhotoUri: Uri): Bitmap {
        return when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                activity?.contentResolver,
                selectedPhotoUri
            )
            else -> {
                val source = ImageDecoder.createSource(activity?.contentResolver!!, selectedPhotoUri)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }
}