package hu.bme.aut.android.chatApp.Adapter_Rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.ChatApplication.Companion.usersList
import hu.bme.aut.android.chatApp.Model.User
import hu.bme.aut.android.chatApp.databinding.ItemUserBinding

class UsersAdapter: ListAdapter<User, UsersAdapter.UsersViewHolder>(ItemCallback)  {

    private var usersViewList = emptyList<User>()

    inner class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        var ivUserImage: ImageView = binding.ivUserImage
        var tvUserName: TextView = binding.tvUserName

        var user : User? = null

        init{

        }
    }

    fun addAll(){
        if(currentConversation != null){
            val users : MutableList<User> = mutableListOf()
            for(user in usersList){
                for(conversation in user.conversations!!) {
                    if (conversation.code == currentConversation?.code)
                        users.add(user)
                }
            }
            usersViewList += users
            submitList(usersViewList)
        }
    }


    override fun getItemCount(): Int {
        return usersViewList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UsersViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UsersAdapter.UsersViewHolder, position: Int) {
        val user = usersViewList[position]
        holder.tvUserName.text = user.userName
        holder.user  = user
        holder.ivUserImage.setImageBitmap(user.profilePicture)

    }

    companion object {
        object ItemCallback : DiffUtil.ItemCallback<User>() {
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

