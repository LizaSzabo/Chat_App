package hu.bme.aut.android.chatApp.Adapter_Rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.ChatApplication.Companion.usersList
import hu.bme.aut.android.chatApp.Model.Message
import hu.bme.aut.android.chatApp.Network.UpdateUser
import hu.bme.aut.android.chatApp.databinding.ItemMessageReceivedBinding
import hu.bme.aut.android.chatApp.databinding.ItemSentMessageBinding


class ChatAdapter : ListAdapter<Message, ChatAdapter.ChatViewHolder>(itemCallback)  {

    var messageList = emptyList<Message>()

    abstract class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

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
        var tvDate: TextView = binding.dateSent
        var message: Message? = null
    }


    inner class ReceivedViewHolder(val binding: ItemMessageReceivedBinding) : ChatViewHolder(binding.root) {
        val tvMessageReceived: TextView = binding.tvMessageReceived
        var tvDate: TextView = binding.dateReceived
        var message: Message? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return when (viewType) {
            TYPE_SENT -> {
                SentViewHolder(
                    ItemSentMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_RECEIVED -> {
                ReceivedViewHolder(
                    ItemMessageReceivedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
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
                holder.tvDate.text = message.date
            }
            is ReceivedViewHolder -> {
                holder.tvMessageReceived.text = message.content
                holder.message = message
                holder.tvDate.text = message.date
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun addAll(){
        if(currentConversation?.messages != null){
        val messages: MutableList<Message> = currentConversation?.messages!!
        messageList += messages
        submitList(messages)}
    }

    override fun getItemViewType(position: Int): Int {
        return when (messageList[position].sender) {
            currentUser?.userName -> TYPE_SENT
            else -> TYPE_RECEIVED
        }
    }

    fun addMessage(message: Message){
        messageList += message
        currentConversation?.messages?.add(message)

        for(user in usersList){
            for(conversation in user.conversations!!){
                if(conversation.code == currentConversation?.code){
                    val index = user.conversations!!.indexOf(conversation)
                    user.conversations!![index] = currentConversation!!

                    UpdateUser(user)
                }
            }
        }
        submitList(messageList)
    }

    fun addAllMessages(messages : List<Message>){
        messageList += messages
        submitList(messageList)
    }

    fun add(message : Message){
        messageList += message
        submitList(messageList)
    }

}