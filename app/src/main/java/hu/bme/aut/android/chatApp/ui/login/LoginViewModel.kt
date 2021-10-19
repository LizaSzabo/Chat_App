package hu.bme.aut.android.chatApp.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.navigation.NavController
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.android.chatApp.ChatApplication
import hu.bme.aut.android.chatApp.Model.User
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

@SuppressLint("StaticFieldLeak")
class LoginViewModel @Inject constructor(
    private val loginPresenter: LoginPresenter
) : RainbowCakeViewModel<LoginViewState>(Initial) {

    private lateinit var context: Context
    private var lastSelected by Delegates.notNull<Int>()
    private var users: List<User> = mutableListOf()

    private fun getUsers() = execute {
        users = loginPresenter.getUsers()
    }

    fun init() {
        getUsers()
        viewState = if (users.isNotEmpty()) UsersInitSuccess else UsersInitError
    }

    fun validUserAndPass(userNameText: String, passwordText: String) : Boolean {
        for (user in users) {
            if (validUser(userNameText, passwordText, user.userName, user.password)) {
                ChatApplication.currentUser = user
                postEvent(ValidUser)
                return true
            }
        }
        postEvent(WrongUser)
        return false
    }


    private fun validUser(userNameText: String, passwordText: String, currentUserName: String, currentPassword: String): Boolean {
        if ((userNameText == currentUserName) && (passwordText == currentPassword)) return true
        return false
    }

    fun onSpinnerItemSelected(position: Int, last_selected: Int, navController: NavController, cxt: Context?) {
        if (cxt != null) {
            context = cxt
        }
        when (position) {
            0 -> setApplicationLocale("en")
            1 -> setApplicationLocale("hu")
            else -> setApplicationLocale("en")
        }
        if (last_selected != position) {
            lastSelected = position
            val action = LoginFragmentDirections.actionLoginFragmentSelf(position)
            navController.navigate(action)
        }
    }

    private fun setApplicationLocale(locale: String) {
        val resources = context.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        config.setLocale(Locale(locale.toLowerCase(Locale.ROOT)))
        resources.updateConfiguration(config, dm)
    }

    object ValidUser : OneShotEvent
    object WrongUser : OneShotEvent

}

