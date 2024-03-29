package hu.bme.aut.android.chatApp.ui.Chat

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.Adapter_Rv.ChatAdapter
import hu.bme.aut.android.chatApp.ChatApplication.Companion.chatFragment
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.Model.Message
import hu.bme.aut.android.chatApp.Network.observeNewMessage
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentChatBinding
import hu.bme.aut.android.chatApp.extensions.resizeByHeight
import hu.bme.aut.android.chatApp.extensions.resizeByWidth
import java.text.SimpleDateFormat
import java.util.*


class ChatFragment : RainbowCakeFragment<ChatViewState, ChatViewModel>(), ChatAdapter.ChatItemClickListener {

    override fun getViewResource() = R.layout.fragment_chat
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var fragmentBinding: FragmentChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private val args: ChatFragmentArgs by navArgs()
    private lateinit var currentConversationId: String
    private lateinit var location: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatFragment = true

        val binding = FragmentChatBinding.bind(view)
        fragmentBinding = binding

        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatButtomToolbar)
        setHasOptionsMenu(true)

        currentConversationId = args.currentConversationId
        location = args.location

        if (location != "0")
            fragmentBinding.text.setText(location)
        else fragmentBinding.text.setText("")

        fragmentBinding.chatButtomToolbar.title = ""
        fragmentBinding.ibSend.setOnClickListener {
            if (fragmentBinding.text.text.toString().isNotEmpty()) {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
                val time = dateFormat.format(Calendar.getInstance().time)

                viewModel.addMessage(currentUser!!.id, fragmentBinding.text.text.toString(), time, currentConversationId)
            }
        }

        fragmentBinding.iwConversationPicture.setOnClickListener {
            val action = ChatFragmentDirections.actionChatFragmentToViewUsersInConversation(currentConversationId)
            findNavController().navigate(action)
        }

        fragmentBinding.conversationTitle.text = currentConversation?.name

        val resized: Bitmap?
        val picture = currentConversation?.picture
        resized = if (picture!!.height > picture.width) {
            picture.resizeByWidth(fragmentBinding.iwConversationPicture.layoutParams.width)
        } else {
            picture.resizeByHeight(fragmentBinding.iwConversationPicture.layoutParams.height)

        }
        fragmentBinding.iwConversationPicture.setImageBitmap(resized)
        fragmentBinding.text.doOnTextChanged { _, _, _, _ ->
            fragmentBinding.text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
        }

        initRecyclerView()
        observeNewMessage(viewModel, chatAdapter)
    }


    private fun initRecyclerView() {
        chatAdapter = ChatAdapter()
        fragmentBinding.rvChat.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvChat.adapter = chatAdapter
        chatAdapter.itemClickListener = this
        viewModel.loadAllMessages(chatAdapter, currentConversationId)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_location) {
            val action = ChatFragmentDirections.actionChatFragmentToMapFragment()
            findNavController().navigate(action)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            ChatViewModel.AddError -> fragmentBinding.text.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun render(viewState: ChatViewState) {
        when (viewState) {
            Initial -> {
                fragmentBinding.text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
            }
            MessageLoadSuccess -> {
                Toast.makeText(context, "Load Succeeded", Toast.LENGTH_LONG).show()
                fragmentBinding.rvChat.scrollToPosition(chatAdapter.itemCount - 1)
            }
            MessageAddError -> {

            }
            MessageAddSuccess -> {
                fragmentBinding.text.setText("")
                fragmentBinding.text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
                fragmentBinding.rvChat.scrollToPosition(chatAdapter.itemCount - 2)
            }
            MessageLoadError -> {
            }
        }.exhaustive
    }

    override fun onItemClick(message: Message) {
        if (isLocation(message.content)) {
            Log.i("content", message.content)
            val action = ChatFragmentDirections.actionChatFragmentToMapFragment(message.content)
            findNavController().navigate(action)
        }
    }

    private fun isLocation(content: String): Boolean {
        return content.startsWith("lat/lng: (")
                && content.endsWith(")")
                && content.contains(",")
                && content.substringAfter('(').substringBefore(',').matches("-?\\d+(\\.\\d+)?".toRegex())
                && content.substringAfter(',').substringBefore(')').matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    override fun onPause() {
        chatFragment = false
        super.onPause()
    }
}