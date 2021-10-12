package hu.bme.aut.android.chatApp.Adapter_Rv

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
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.ItemConversationBinding

class ConversationsAdapter : ListAdapter<Conversation, ConversationsAdapter.ConversationViewHolder>(ItemCallback) {

    var itemClickListener: ConversationItemClickListener? = null
    private var conversationList = emptyList<Conversation>()

    @SuppressLint("ResourceAsColor")
    inner class ConversationViewHolder(val binding: ItemConversationBinding) : RecyclerView.ViewHolder(binding.root) {
        var ivConversationImage: ImageView = binding.ivConversationImage
        val ibStar: ImageButton = binding.ibStar
        val tvConversationName: TextView = binding.tvConversationName

        var conversation: Conversation? = null

        init {
            itemView.setOnClickListener {
                conversation?.let { itemClickListener?.onItemClick(it) }
            }

            itemView.setOnLongClickListener { view ->
                view.setBackgroundResource(R.color.background)
                conversation?.let { itemClickListener?.onItemLongClick(adapterPosition, view, it) }
                true
            }

            ibStar.tag = R.drawable.star_icon
            ibStar.setOnClickListener {
                when (conversation?.favourite) {
                    false -> {
                        if (conversation != null) {
                            ibStar.tag = R.drawable.ic_baseline_star_24
                            ibStar.setImageResource(R.drawable.ic_baseline_star_24)
                            conversation?.favourite = true
                            itemClickListener?.onItemStartClick(conversation!!, true)
                        }
                    }
                    true -> {
                        if (conversation != null) {
                            ibStar.tag = R.drawable.star_icon
                            ibStar.setImageResource(R.drawable.star_icon)
                            conversation?.favourite = false
                            itemClickListener?.onItemStartClick(conversation!!, false)
                        }
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
        holder.conversation = conversation
        holder.ivConversationImage.setImageBitmap(conversation.picture)
        when (conversation.favourite) {
            false -> {
                holder.ibStar.setImageResource(R.drawable.star_icon)
            }
            true -> {
                holder.ibStar.setImageResource(R.drawable.ic_baseline_star_24)
            }
        }
    }

    interface ConversationItemClickListener {
        fun onItemClick(conversation: Conversation)
        fun onItemLongClick(position: Int, view: View, conversation: Conversation): Boolean
        fun onItemStartClick(conversation: Conversation, favourite: Boolean)
    }

    companion object {
        object ItemCallback : DiffUtil.ItemCallback<Conversation>() {
            override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
                return false
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

    fun addConversation(conversation: Conversation) {
        conversationList += conversation
        submitList(conversationList)
    }

    /* fun addAll(conversation: String){
         val convs: MutableList<Conversation>


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
     }*/

    fun addAllConversations(conversations: List<Conversation>, searchText : String) {
        val selectedConversations= mutableListOf<Conversation>()

        for(c in conversations)
            if(c.name.contains(searchText, ignoreCase = true))
                selectedConversations.add(c)

        conversationList -= conversationList
        conversationList += selectedConversations
        submitList(conversationList)
    }

    fun updateConversation(conversation: Conversation, pos: Int) {
        val modifiedConversationList = conversationList.toMutableList()
        modifiedConversationList[pos] = conversation
        conversationList = modifiedConversationList.toList()
        submitList(conversationList)
    }

    fun updateConversationPicture(conversation: Conversation, pos: Int) {
        val modifiedConversationList = conversationList.toMutableList()
        modifiedConversationList[pos] = conversation
        conversationList = modifiedConversationList.toList()
        submitList(conversationList)
    }
}