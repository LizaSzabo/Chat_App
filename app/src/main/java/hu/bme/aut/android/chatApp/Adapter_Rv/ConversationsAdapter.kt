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
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chatApp.ChatApplication.Companion.usersList
import hu.bme.aut.android.chatApp.Model.Conversation
import hu.bme.aut.android.chatApp.Network.UpdateUser
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.ItemConversationBinding
import java.util.ArrayList

class ConversationsAdapter: ListAdapter<Conversation, ConversationsAdapter.ConversationViewHolder>(ItemCallback)  {

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
            ibStar.setOnClickListener{
                var index = 0
                var u: hu.bme.aut.android.chatApp.Model.User? = null
                for(user in usersList){
                    if(user.userName == currentUser?.userName){
                        index = user.conversations!!.indexOf(conversation)
                        u = user
                    }
                }
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

                u?.conversations!![index] = conversation!!
                UpdateUser(u)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ConversationViewHolder(ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val conversation = conversationList[position]
        holder.tvConversationName.text = conversation.name
        holder.conversation  = conversation
        holder.ivConversationImage.setImageBitmap(conversation.picture)
        when(conversation.favourite) {
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
        object ItemCallback : DiffUtil.ItemCallback<Conversation>() {
            override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
                return oldItem.code == newItem.code
            }

            override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return conversationList.size
    }

   /* fun deleteConversation(position: Int) {
        conversationList = conversationList.filterIndexed { index, _ -> index != position }
        currentUser?.conversations?.remove(currentConversation)
        submitList(conversationList)

        for(user in usersList){
            if(user.userName == currentUser?.userName){
                user.conversations?.remove(currentConversation)
                UpdateUser(user)
            }
        }

    }*/

    fun deleteConversation(position: Int){
        conversationList = conversationList.filterIndexed { index, _ -> index != position }
        submitList(conversationList)
    }

    fun addAll(conversation: String){
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
    }

    fun addAllConversations(conversations: List<Conversation>)
    {
        conversationList += conversations
        submitList(conversationList)
    }

   /* fun updateConversation(conv: Conversation, pos: Int){
        conversationList = conversationList.filterIndexed { index, _ -> index != pos}
        conversationList += conv

        for(user in usersList){
            for(conversation in user.conversations!!){
                if(conversation.code == currentConversation?.code){
                    val con = Conversation(conv.id, conv.name, conv.type, conv.messages, conv.picture, conversation.favourite, conv.code)
                    val index = user.conversations!!.indexOf(conversation)
                    user.conversations!![index] = con

                    UpdateUser(user)
                }
            }
        }

        submitList(conversationList)
    }*/

    fun updateConversation(conversation : Conversation, pos : Int){
        //val c = conversationList.elementAt(pos).copy(name = "ss")
        val newList = ArrayList(conversationList)
        newList[pos] = conversation
        submitList(newList)
        conversationList = newList
    }

    /*fun updateConversationPicture(conv: Conversation, pos: Int){

        for(user in usersList){
            for(conversation in user.conversations!!){
                if(conversation.code == conv.code){
                    val index = user.conversations!!.indexOf(conversation)
                    user.conversations!![index] = conv

                  //  UpdateUser(user)
                }
            }
        }

        submitList(conversationList)
    }*/

    fun updateConversationPicture(conversation: Conversation, pos : Int){
        conversationList += conversation
        submitList(conversationList)
    }

    fun addConversation(conversation: Conversation){
        conversationList += conversation

        for(user in usersList){
            if(user.userName ==  currentUser?.userName){
                user.conversations?.add(conversation)
                currentUser = user
                UpdateUser(user)
            }
        }
        submitList(conversationList)
    }

    fun addConversationToUser(username : String){
        for(user in usersList){
                if(user.userName == username){
                    val conversation = currentConversation?.name?.let {
                        currentConversation?.type?.let { it1 ->
                            currentConversation?.picture?.let { it2 ->
                                currentConversation?.code?.let { it3 ->
                                    Conversation(
                                        currentConversation!!.id, it, it1, currentConversation?.messages,
                                        it2, false, it3
                                    )
                                }
                            }
                        }
                    }
                    if (conversation != null) {
                        user.conversations?.add(conversation)
                    }

                    UpdateUser(user)

                }
        }
    }

}