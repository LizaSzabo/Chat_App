package hu.bme.aut.android.chatApp.ui.editusername

import android.util.Log
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class EditUserViewModel @Inject constructor(
    private val editUserNamePresenter: EditUserNamePresenter
) : RainbowCakeViewModel<EditUserNameViewState>(Initial) {
    private var alreadyExists = true
    private var updated = false

    fun updateUserName(newUserName: String) = execute {
        alreadyExists = editUserNamePresenter.existsUserName(newUserName)
        if (alreadyExists) {
            viewState = UpdateCancelled
            postEvent(EditCancelled)
        } else {
            Log.i("step", "execute begin")
            updated = editUserNamePresenter.updateUserName(newUserName)
            Log.i("step", "suspend ended")
            Log.i("step", "execute continuing")
            viewState = if (updated) EditUserNameSuccess else EditUserNameError
        }
        Log.i("step", "execute ending")
    }

    object EditCancelled : OneShotEvent

}