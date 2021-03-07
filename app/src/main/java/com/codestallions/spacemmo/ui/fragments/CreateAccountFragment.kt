package com.codestallions.spacemmo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.codestallions.spacemmo.R
import com.codestallions.spacemmo.SpaceMMO
import com.codestallions.spacemmo.databinding.FragmentCreateAccountBinding
import com.codestallions.spacemmo.model.PlayerModel
import com.codestallions.spacemmo.ui.viewmodel.LoginViewModel
import com.codestallions.spacemmo.util.StringUtil
import com.google.common.base.Optional
import com.google.firebase.auth.FirebaseUser

class CreateAccountFragment : Fragment(), ICreateAccountFragment {
    private lateinit var accountBinding: FragmentCreateAccountBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private var email: String? = null
    private var password: String? = null
    private var confirmPassword: String? = null
    private var displayName: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        accountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false)
        accountBinding.loginViewModel = loginViewModel
        accountBinding.iCreateAccountFragment = this
        return accountBinding.root
    }

    override fun handleAccountCreation() {
        email = accountBinding.createAccountEmailEntry.text.toString().trim { it <= ' ' }
        password = accountBinding.createAccountPasswordEntry.text.toString().trim { it <= ' ' }
        confirmPassword = accountBinding.createAccountConfirmPasswordEntry.text.toString().trim { it <= ' ' }
        displayName = accountBinding.createAccountDisplayNameEntry.text.toString().trim { it <= ' ' }
        if (StringUtil.isTextEntered(email, password, confirmPassword, displayName)) {
            if (password == confirmPassword) {
                loginViewModel.createAccountWithEmail(email, password)
                        .observe(viewLifecycleOwner, Observer { playerModel: Optional<PlayerModel> ->
                            if (playerModel.isPresent && SpaceMMO.getAuth().currentUser != null) {
                                playerModel.get().displayName = displayName
                                loginViewModel.postNewPlayerProfile(playerModel.get())
                                sendEmailVerification(SpaceMMO.getAuth().currentUser)
                            }
                        })
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser?) {
        loginViewModel.sendVerificationEmail(user).observe(viewLifecycleOwner, Observer { isSuccessful: Boolean ->
            if (isSuccessful) {
                Toast.makeText(context, "Verification email has been sent", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        })
    }
}