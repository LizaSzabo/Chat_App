package hu.bme.aut.android.chat_app.Adapter_Rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.ItemConversationBinding
import hu.bme.aut.android.chat_app.databinding.ItemMessageReceivedBinding
import hu.bme.aut.android.chat_app.databinding.ItemSentMessageBinding

class ChatAdapter : ListAdapter<Message, ChatAdapter.ChatViewHolder>(itemCallback)  {

    var messageList = emptyList<Message>()

    abstract class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        private const val TYPE_SENT = 0
        private const val TYPE_RECEIVED = 1

        object itemCallback : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class SentViewHolder(val binding: ItemSentMessageBinding) : ChatViewHolder(binding.root) {
        val tvMessageSent: TextView = binding.tvMessageSent
        var message: Message? = null
    }


    inner class ReceivedViewHolder(val binding: ItemMessageReceivedBinding) : ChatViewHolder(binding.root) {
        val tvMessageReceived: TextView = binding.tvMessageReceived
        var message: Message? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return when (viewType) {
            TYPE_SENT-> {
                SentViewHolder(ItemSentMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            TYPE_RECEIVED -> {
                ReceivedViewHolder(ItemMessageReceivedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messageList[position]

        when (holder) {
            is SentViewHolder -> {
                holder.tvMessageSent.text = message.content
                holder.message = message
            }
            is ReceivedViewHolder -> {
                holder.tvMessageReceived.text = message.content
                holder.message = message
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun addAll(){
        val messages: MutableList<Message> =
            currentConversation?.messages!!  //(Message("User1", "second", "Hello"), Message("User1", "second", "Szia"),
          //  Message("User2", "second", "naaaaaaaagyon hossszuuuuuuuuuuuuuuuu szoveeeeeeeeeeeeeeeg"))
        messageList += messages
        submitList(messages)
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = messageList[position].sender
        return when (comparable) {
            currentUser?.userName -> TYPE_SENT
            else -> TYPE_RECEIVED
        }
    }

    fun addMessage(message: Message){
        messageList += message
        submitList( messageList)
    }


}