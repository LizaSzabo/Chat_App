package hu.bme.aut.android.chat_app.Adapter_Rv

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.ItemConversationBinding
import kotlinx.android.synthetic.main.dialog_change_password.*

class ConversationsAdapter: ListAdapter<Conversation, ConversationsAdapter.ConversationViewHolder>(itemCallback)  {

    var itemClickListener: ConversationItemClickListener? = null
    var conv = Conversation("first", "private")
    var conv2 = Conversation("second", "private")
    var conversationList = mutableListOf<Conversation>(Conversation("first", "private"), conv2)


    inner class ConversationViewHolder(val binding: ItemConversationBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivConversationImage: ImageView = binding.ivConversationImage
        val ibStar: ImageButton = binding.ibStar
        val tvConversationName: TextView = binding.tvConversationName


        var conversation: Conversation? = null

        init {
            itemView.setOnClickListener {
                conversation?.let { itemClickListener?.onItemClick(it) }
            }


            ibStar.setOnClickListener{
                when(ibStar.tag){
                    R.drawable.star_icon-> ibStar.setImageResource(R.drawable.ic_baseline_star_24)
                    R.drawable.ic_baseline_star_24 -> ibStar.setImageResource(R.drawable.star_icon)
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
}