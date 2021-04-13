package hu.bme.aut.android.chat_app.ui.Chat

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chat_app.Adapter_Rv.ChatAdapter
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentChatBinding
import hu.bme.aut.android.chat_app.ui.Login.LoginFragmentDirections
import java.text.SimpleDateFormat
import java.util.*

class ChatFragment : RainbowCakeFragment<ChatViewState, ChatViewModel>() {

    private lateinit var fragmentBinding: FragmentChatBinding
    private lateinit var chatAdapter: ChatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentChatBinding.bind(view)
        fragmentBinding= binding

        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.chatButtomToolbar)
        setHasOptionsMenu(true)


       // fragmentBinding.chatTopToolbar.inflateMenu(R.menu.chat_menu)
        fragmentBinding.chatButtomToolbar.title = ""
        fragmentBinding.ibSend.setOnClickListener{
            if( fragmentBinding.text.text.toString().isNotEmpty()) {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
                 val time = dateFormat.format(Calendar.getInstance().time)
                var message = currentUser?.userName?.let { it1 ->
                    hu.bme.aut.android.chat_app.Model.Message(
                        it1,
                        "second",
                        fragmentBinding.text.text.toString(), time
                    )
                }
                if (message != null) {
                    chatAdapter.addMessage(message)
                }
                fragmentBinding.text.setText("")
            }
        }


        fragmentBinding.iwConversationPicture.setOnClickListener{
            val action = ChatFragmentDirections.actionChatFragmentToViewUsersInConversation()
            findNavController().navigate(action)
        }

       // fragmentBinding.profilepicture.setImageURI(currentConversation?.picture)

        fragmentBinding.conversationTitle.text = currentConversation?.name


        var resized: Bitmap? = null
        var picture = MediaStore.Images.Media.getBitmap(context?.contentResolver, currentConversation?.picture)
        resized = if( picture.height!! > picture.width!!){
            picture.resizeByWidth( fragmentBinding.iwConversationPicture.layoutParams.width)
        } else {
            picture.resizeByHeight( fragmentBinding.iwConversationPicture.layoutParams.height)

        }
        fragmentBinding.iwConversationPicture.setImageBitmap(resized)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        chatAdapter = ChatAdapter()
        fragmentBinding.rvChat.layoutManager = LinearLayoutManager( context)
        fragmentBinding.rvChat.adapter =  chatAdapter
       // chatAdapter.itemClickListener = this
        chatAdapter.addAll()
    }


    private fun Bitmap.resizeByHeight(height:Int):Bitmap{
        val ratio:Float = this.height.toFloat() / this.width.toFloat()
        val width:Int = Math.round(height / ratio)

        return Bitmap.createScaledBitmap(
            this,
            width,
            height,
            false
        )
    }

    private fun Bitmap.resizeByWidth(width:Int):Bitmap{
        val ratio:Float = this.width.toFloat() / this.height.toFloat()
        val height:Int = Math.round(width / ratio)

        return Bitmap.createScaledBitmap(
            this,
            width,
            height,
            false
        )
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

    override fun getViewResource() = R.layout.fragment_chat

    override fun provideViewModel()= getViewModelFromFactory()

    override fun render(viewState: ChatViewState) {
        when(viewState){
         Initial -> {

         }
            else ->{

            }
        }.exhaustive
    }

}