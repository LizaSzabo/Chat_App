package hu.bme.aut.android.chat_app.Adapter_Rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.chat_app.ChatApplication
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.Model.Conversation
import hu.bme.aut.android.chat_app.Model.Message
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.ItemConversationBinding
import hu.bme.aut.android.chat_app.databinding.ItemUserBinding

class UsersAdapter: ListAdapter<User, UsersAdapter.UsersViewHolder>(itemCallback)  {

    var usersviewList = emptyList<User>()

    inner class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        var ivUserImage: ImageView = binding.ivUserImage
        var tvUserName: TextView = binding.tvUserName

        var user : User? = null

        init{

        }
    }

    fun addAll(){
        if(currentConversation != null){
            var users : MutableList<User> = mutableListOf()
            for(user in usersList){
                if(user.conversations?.contains(currentConversation) == true)
                    users.add(user)
            }
            usersviewList += users
            submitList(usersviewList)
        }
    }


    override fun getItemCount(): Int {
        return usersviewList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UsersViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UsersAdapter.UsersViewHolder, position: Int) {
        val user = usersviewList[position]
        holder.tvUserName.text = user.userName
        holder.user  = user
        holder.ivUserImage.setImageBitmap(user.profilePicture)

    }

    companion object {
        object itemCallback : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}

