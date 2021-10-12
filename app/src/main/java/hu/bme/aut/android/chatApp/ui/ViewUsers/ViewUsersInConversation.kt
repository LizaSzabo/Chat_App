package hu.bme.aut.android.chatApp.ui.ViewUsers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.android.chatApp.Adapter_Rv.UsersAdapter
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.databinding.FragmentViewUsersBinding
import hu.bme.aut.android.chatApp.ui.Chat.ChatFragmentArgs


class ViewUsersInConversation : RainbowCakeFragment<ViewUsersViewState, ViewUsersViewModel>() {

    override fun getViewResource(): Int = R.layout.fragment_view_users
    override fun provideViewModel(): ViewUsersViewModel = getViewModelFromFactory()

    private lateinit var fragmentBinding: FragmentViewUsersBinding
    private lateinit var usersAdapter: UsersAdapter
    private val args: ChatFragmentArgs by navArgs()
    private  lateinit var currentConversationId : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentViewUsersBinding.bind(view)
        fragmentBinding = binding

        currentConversationId = args.currentConversationId

        fragmentBinding.iwConversationPicture.setImageBitmap(currentConversation?.picture)

        initRecyclerView()
        viewModel.addAllUsers(usersAdapter, currentConversationId)
    }

    private fun initRecyclerView() {
        usersAdapter = UsersAdapter()
        fragmentBinding.rvUsers.layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvUsers.adapter = usersAdapter
       // usersAdapter.addAll()
    }


    override fun render(viewState: ViewUsersViewState) {
        when (viewState) {
            Initial -> {

            }
            UsersLoadError -> {
            }
            UsersLoadSuccess -> {
                Toast.makeText(context, "Users loaded!", Toast.LENGTH_LONG).show()
            }
        }.exhaustive
    }
}