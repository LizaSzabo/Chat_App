package hu.bme.aut.android.chat_app.ui.Chat

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chat_app.*
import hu.bme.aut.android.chat_app.Adapter_Rv.ChatAdapter
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.Network.observeData
import hu.bme.aut.android.chat_app.Network.querys
import hu.bme.aut.android.chat_app.databinding.FragmentChatBinding
import kotlinx.android.synthetic.main.fragment_chat.*
import okhttp3.internal.notify
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

        fragmentBinding.chatButtomToolbar.title = ""
        fragmentBinding.ibSend.setOnClickListener{
            if( fragmentBinding.text.text.toString().isNotEmpty()) {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
                val time = dateFormat.format(Calendar.getInstance().time)
                val message = currentUser?.userName?.let { it1 ->
                    hu.bme.aut.android.chat_app.Model.Message(
                        it1,
                        "second",
                        fragmentBinding.text.text.toString(), time
                    )
                }
                if (message != null) {
                    chatAdapter.addMessage(message)
                    currentConversation?.let { it1 -> querys(it1?.picture) }
                }
                fragmentBinding.text.setText("")


              //  FloatingService.notificationUpdate(fragmentBinding.text.text.toString())
            }
        }


        fragmentBinding.iwConversationPicture.setOnClickListener{
            val action = ChatFragmentDirections.actionChatFragmentToViewUsersInConversation()
            findNavController().navigate(action)
        }

       // fragmentBinding.profilepicture.setImageURI(currentConversation?.picture)

        fragmentBinding.conversationTitle.text = currentConversation?.name


        val resized: Bitmap?
        val picture =  currentConversation?.picture
        resized = if( picture!!.height > picture.width){
            picture.resizeByWidth(fragmentBinding.iwConversationPicture.layoutParams.width)
        } else {
            picture.resizeByHeight(fragmentBinding.iwConversationPicture.layoutParams.height)

        }
        fragmentBinding.iwConversationPicture.setImageBitmap(resized)
        initRecyclerView()

      val uri: Uri = Uri.parse("android.resource://hu.bme.aut.android.chat_app/drawable/addprofile")
        val b: Bitmap = MediaStore.Images.Media.getBitmap(
            (activity as AppCompatActivity).contentResolver,
            uri
        )



    }

    private fun initRecyclerView(){
        chatAdapter = ChatAdapter()
        fragmentBinding.rvChat.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvChat.adapter =  chatAdapter
       // chatAdapter.itemClickListener = this
        chatAdapter.addAll()
    }


    private fun Bitmap.resizeByHeight(height: Int):Bitmap{
        val ratio:Float = this.height.toFloat() / this.width.toFloat()
        val width:Int = Math.round(height / ratio)

        return Bitmap.createScaledBitmap(
            this,
            width,
            height,
            false
        )
    }

    private fun Bitmap.resizeByWidth(width: Int):Bitmap{
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
        inflater.inflate(R.menu.chat_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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