package hu.bme.aut.android.chat_app.ui.ViewUsers

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import hu.bme.aut.android.chat_app.Adapter_Rv.UsersAdapter
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.FragmentViewUsersBinding


class ViewUsersInConversation: RainbowCakeFragment<ViewUsersViewState, ViewUsersViewModel>() {

    private lateinit var fragmentBinding: FragmentViewUsersBinding
    private lateinit var usersAdapter: UsersAdapter

    override fun onViewCreated(view: View,  savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentViewUsersBinding.bind(view)
        fragmentBinding = binding

        fragmentBinding.iwConversationPicture.setImageBitmap(currentConversation?.picture)

        initRecyclerView()
    }

    private fun initRecyclerView(){
        usersAdapter = UsersAdapter()
        fragmentBinding.rvUsers.layoutManager = LinearLayoutManager( context)
        fragmentBinding.rvUsers.adapter = usersAdapter
        usersAdapter.addAll()
    }

    override fun getViewResource(): Int = R.layout.fragment_view_users

    override fun provideViewModel(): ViewUsersViewModel =  getViewModelFromFactory()


    override fun render(viewState: ViewUsersViewState) {
        when(viewState){
            Initial -> {

            }
            else -> {}
        }
    }
}