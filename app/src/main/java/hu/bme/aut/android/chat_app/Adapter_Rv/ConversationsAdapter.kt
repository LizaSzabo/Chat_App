package hu.bme.aut.android.chat_app.Adapter_Rv

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
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
        var ivConversationImage: ImageView = binding.ivConversationImage
        val ibStar: ImageButton = binding.ibStar
        val tvConversationName: TextView = binding.tvConversationName
      //  val ivConversation: ImageView = binding.ivConversationImage

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
            ibStar.setOnClickListener{
                when(conversation?.favourite){
                    false-> {
                        ibStar.tag = R.drawable.ic_baseline_star_24
                        ibStar.setImageResource(R.drawable.ic_baseline_star_24)
                        conversation?.favourite = true
                    }
                   true ->{
                        ibStar.tag = R.drawable.star_icon
                        ibStar.setImageResource(R.drawable.star_icon)
                        conversation?.favourite = false
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
        holder.ivConversationImage.setImageURI(conversation.picture)
        when(conversation?.favourite) {
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
        currentUser?.conversations?.remove(currentConversation)
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
        for(user in usersList){
            for(conversation in user?.conversations!!){
                if(conversation.name == currentConversation?.name){
                    val index = user.conversations!!.indexOf(conversation)
                    user.conversations!!.set(index, conv)
                }
            }
        }

        submitList(conversationList)
    }

    fun UpdateConversationPicture(conv: Conversation, pos: Int){
       /* conversationList = conversationList.filterIndexed { index, _ -> index != pos}
        conversationList += conv*/

        currentUser?.conversations = conversationList as MutableList<Conversation>
        for(user in usersList){
            for(conversation in user?.conversations!!){
                if(conversation.name == conv.name){
                    val index = user.conversations!!.indexOf(conversation)
                    user.conversations!!.set(index, conv)
                }
            }
        }

        submitList(conversationList)
    }

    fun AddConversation(conversation: Conversation){
        conversationList += conversation

        for(user in usersList){
            if(user.userName ==  currentUser?.userName){
                user.conversations?.add(conversation)
                currentUser = user
            }
        }
        submitList(conversationList)
    }

    fun AddConversationToUser(username : String){
        for(user in usersList){
                if(user.userName == username){
                    val conversation = currentConversation?.name?.let {
                        currentConversation?.type?.let { it1 ->
                            currentConversation?.picture?.let { it2 ->
                                Conversation(
                                    it, it1, currentConversation?.messages,
                                    it2, false)
                            }
                        }
                    }
                    if (conversation != null) {
                        user.conversations?.add(conversation)
                    }
                }
        }
    }
}