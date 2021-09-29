package hu.bme.aut.android.chatApp.ui.editconversation

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class EditConversationViewModel @Inject constructor(
    private val editConversationPresenter: EditConversationPresenter
) : RainbowCakeViewModel<EditConversationViewState>(Initial)
{}