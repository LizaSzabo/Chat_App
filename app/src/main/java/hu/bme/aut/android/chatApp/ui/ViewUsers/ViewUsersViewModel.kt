package hu.bme.aut.android.chatApp.ui.ViewUsers

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.Adapter_Rv.UsersAdapter
import javax.inject.Inject

class ViewUsersViewModel @Inject constructor(
    private val viewUsersPresenter: ViewUsersPresenter
) : RainbowCakeViewModel<ViewUsersViewState>(Initial) {

    fun addAllUsers(adapter: UsersAdapter, conversationId: String) = execute {
        val users = viewUsersPresenter.getUsersOfConversation(conversationId)
        if (users.isEmpty()) {
            viewState = UsersLoadError
        } else {
            viewState = UsersLoadSuccess
            adapter.addAllUsers(users)
        }
    }
}