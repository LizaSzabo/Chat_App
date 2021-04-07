package hu.bme.aut.android.chat_app.Adapter_Rv

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.ItemConversationBinding
import kotlinx.android.synthetic.main.dialog_change_password.*
import java.util.*
import kotlin.collections.ArrayList

class ConversationsAdapter: ListAdapter<Conversation, ConversationsAdapter.ConversationViewHolder>(itemCallback)  {

    var itemClickListener: ConversationItemClickListener? = null
   // var conv = Conversation("first", "private")
   // var conv2 = Conversation("second", "private")
    var conversationList = emptyList<Conversation>()
    //var conversationList = mutableListOf<Conversation>(Conversation("first", "private"), conv2, conv, conv, conv, conv, conv, conv, conv, conv)


    @SuppressLint("ResourceAsColor")
    inner class ConversationViewHolder(val binding: ItemConversationBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivConversationImage: ImageView = binding.ivConversationImage
        val ibStar: ImageButton = binding.ibStar
        val tvConversationName: TextView = binding.tvConversationName


        var conversation: Conversation? = null

        init {
            itemView.setOnClickListener {
                conversation?.let { itemClickListener?.onItemClick(it) }
            }

            itemView.setOnLongClickListener { view ->
                view.setBackgroundResource(R.color.background)
                itemClickListener?.onItemLongClick(adapterPosition, view)
                true
            }

            ibStar.tag = R.drawable.star_icon
            ibStar.setOnClickListener{
                when(ibStar.tag){
                    R.drawable.star_icon-> {
                        ibStar.tag = R.drawable.ic_baseline_star_24
                        ibStar.setImageResource(R.drawable.ic_baseline_star_24)
                    }
                    R.drawable.ic_baseline_star_24 ->{
                        ibStar.tag = R.drawable.star_icon
                        ibStar.setImageResource(R.drawable.star_icon)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ConversationViewHolder(ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val conversation = conversationList[position]
        holder.tvConversationName.text = conversation.name
        holder.conversation  = conversation
    }

    interface ConversationItemClickListener {
        fun onItemClick(conversation: Conversation)
        fun onItemLongClick(position: Int, view: View): Boolean
    }

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Conversation>() {
            override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return conversationList.size
    }

    fun deleteConversation(position: Int) {
        conversationList = conversationList.filterIndexed { index, _ -> index != position }
        submitList(conversationList)
    }

    fun addAll(conversation: String){
        //val convs = mutableListOf<Conversation>(Conversation("first", "private"), conv2, conv, conv, conv, conv, conv, conv, conv, conv)
        var  convs = mutableListOf<Conversation>()
        if(conversation.isEmpty()) {
            conversationList = emptyList()
            if(currentUser?.conversations != null){
            convs = currentUser?.conversations!!
            conversationList += convs
            submitList(conversationList)
            }
        }
        else{
            conversationList = emptyList()
            for(c in currentUser?.conversations!!){
                if(c.name.contains(conversation, ignoreCase = true))
                    conversationList += c
                    submitList(conversationList)
            }
        }
    }

    fun UpdateConversation(conv: Conversation, pos: Int){
        conversationList = conversationList.filterIndexed { index, _ -> index != pos}
        conversationList += conv
        currentUser?.conversations = conversationList as MutableList<Conversation>
        submitList(conversationList)
    }
}