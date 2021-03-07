package com.codestallions.spacemmo.ui.fragments

import android.content.Intent
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
import com.codestallions.spacemmo.databinding.FragmentLoginBinding
import com.codestallions.spacemmo.model.AuthUserModel
import com.codestallions.spacemmo.ui.activities.MainActivity
import com.codestallions.spacemmo.ui.viewmodel.LoginViewModel
import com.google.common.base.Optional

class LoginFragment : Fragment(), ILoginFragment {
    private lateinit var loginBinding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        loginBinding.loginViewModel = loginViewModel
        loginBinding.iLoginFragment = this
        handleLoginNavigation()
        return loginBinding.root
    }

    override fun handleLoginEntry() {
        val emailEntry = loginBinding.loginEmailEntry.text.toString().trim { it <= ' ' }
        val passwordEntry = loginBinding.loginPasswordEntry.text.toString().trim { it <= ' ' }
        if (emailEntry.isNotEmpty() && passwordEntry.isNotEmpty()) {
            loginViewModel.signInWithEmail(emailEntry, passwordEntry).observe(this, Observer { user: Optional<AuthUserModel?> ->
                if (user.isPresent && isUserSignedInValid) {
                    handleLoginNavigation()
                } else if (user.isPresent && !isUserSignedInValid) {
                    Toast.makeText(activity, "Please verify your email address to sign in.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, "Invalid username and password.", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(activity, "Please complete entries.", Toast.LENGTH_LONG).show()
        }
    }

    override fun navigateToAccountCreation() {
        val newFragment: Fragment = CreateAccountFragment()
        parentFragmentManager.beginTransaction().replace(R.id.login_root_container, newFragment).addToBackStack(CREATE_TAG).commit()
    }

    private fun handleLoginNavigation() {
        // Query player profile in DB, update 'verified' to true and place player in world if it was false.
        if (isUserSignedInValid) {
            loginViewModel.handleLoginRequirements(SpaceMMO.getAuth().currentUser!!.uid)
                    .observe(viewLifecycleOwner, Observer { isSuccessful: Boolean ->
                        if (isSuccessful) {
                            navigateToMainActivity()
                        } else {
                            Toast.makeText(context, "Something went wrong. Please try again later.", Toast.LENGTH_LONG).show()
                        }
                    })
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        SpaceMMO.getSession().saveLoginExpirationTime(context)
        startActivity(intent)
    }

    private val isUserSignedInValid: Boolean
        get() {
            val user = SpaceMMO.getAuth().currentUser
            return user != null && user.isEmailVerified
        }

    companion object {
        private val TAG = LoginFragment::class.java.simpleName
        const val CREATE_TAG = "CREATE_TAG"
    }
}