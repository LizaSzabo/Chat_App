package hu.bme.aut.android.chatApp.ui.Chat

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
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
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentChatBinding
import java.text.SimpleDateFormat
import java.util.*


class ChatFragment : RainbowCakeFragment<ChatViewState, ChatViewModel>() {

    override fun getViewResource() = R.layout.fragment_chat
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var fragmentBinding: FragmentChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private val args: ChatFragmentArgs by navArgs()
    private lateinit var currentConversationId: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentChatBinding.bind(view)
        fragmentBinding = binding

        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatButtomToolbar)
        setHasOptionsMenu(true)

        currentConversationId = args.currentConversationId


        fragmentBinding.chatButtomToolbar.title = ""
        fragmentBinding.ibSend.setOnClickListener {
            if (fragmentBinding.text.text.toString().isNotEmpty()) {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
                val time = dateFormat.format(Calendar.getInstance().time)


                viewModel.addMessage(currentUser!!.userName, "receiver", fragmentBinding.text.text.toString(), time, currentConversationId)
                /*if (message != null) {
                    chatAdapter.addMessage(message)
                    messageText = "Waiting for messages..."
                }*/

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
    }

    private fun initRecyclerView() {
        chatAdapter = ChatAdapter()
        fragmentBinding.rvChat.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvChat.adapter = chatAdapter
        //chatAdapter.addAll()
        viewModel.loadAllMessages(chatAdapter, currentConversationId)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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
                fragmentBinding.text.setText("")
                fragmentBinding.text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
            }
            MessageLoadSuccess -> {
                Toast.makeText(context, "Load Succeeded", Toast.LENGTH_LONG).show()
            }
            MessageAddError -> {

            }
            MessageAddSuccess -> {
                fragmentBinding.text.setText("")
                fragmentBinding.text.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
            }
            MessageLoadError -> {
            }
        }.exhaustive
    }
}