package hu.bme.aut.android.chat_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.datastore.generated.model.User.USER_NAME
import hu.bme.aut.android.chat_app.ChatApplication
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentUser
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.databinding.DialogEditUserNameBinding
import hu.bme.aut.android.chat_app.databinding.FragmentEditProfileBinding

class EditUserNameDialog(var editbinding: FragmentEditProfileBinding): DialogFragment() {
    private lateinit var binding: DialogEditUserNameBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditUserNameBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener{
            if(binding.editTextLoginName.text.toString().isEmpty()){
                binding.editTextLoginName.error = getString(R.string.user_not_empty)
            }else {
                val user = currentUser
                val name = user?.userName
                currentUser?.userName = binding.editTextLoginName.text.toString()
                usersList.find { it == user }?.userName = currentUser?.userName.toString()
                if(user?.conversations != null) {
                    for (conv in user?.conversations!!) {
                        for (message in conv.messages!!) {
                            if (message.sender == name) {
                                message.sender = binding.editTextLoginName.text.toString()
                            }
                        }
                    }
                }
                editbinding.tvUserName.text = ChatApplication.currentUser?.userName


                Amplify.DataStore.query(com.amplifyframework.datastore.generated.model.User::class.java,
                    Where.matches(com.amplifyframework.datastore.generated.model.User.USER_NAME.eq(name)),
                    { matches ->
                        if (matches.hasNext()) {
                            val original = matches.next()
                            val edited = original.copyOfBuilder()
                                .userName(binding.editTextLoginName.text.toString())
                                .build()
                            Amplify.DataStore.save(edited,
                                { Log.i("MyAmplifyApp", "Updated a post") },
                                { Log.e("MyAmplifyApp", "Update failed", it) }
                            )
                        }
                    },
                    { Log.e("MyAmplifyApp", "Query failed", it) }
                )

                dialog?.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener{
            dialog?.dismiss()
        }
        return binding.root
    }


}