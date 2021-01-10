package com.codestallions.spacemmo.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.databinding.FragmentLoginBinding;
import com.codestallions.spacemmo.ui.activities.MainActivity;
import com.codestallions.spacemmo.ui.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment implements ILoginFragment {

    private EditText emailEditText;
    private EditText passwordEditText;

    private String emailEntry;
    private String passwordEntry;

    private LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoginBinding loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);
        loginBinding.setLoginViewModel(loginViewModel);
        loginBinding.setILoginFragment(this);

        emailEditText = loginBinding.getRoot().findViewById(R.id.login_email_entry);
        passwordEditText = loginBinding.getRoot().findViewById(R.id.login_password_entry);
        Button loginButon = loginBinding.getRoot().findViewById(R.id.login_confirm_button);
        loginButon.setOnClickListener(view -> handleLoginEntry());
        return loginBinding.getRoot();
    }

    @Override
    public void handleLoginEntry() {
        emailEntry = emailEditText.getText().toString().trim();
        passwordEntry = passwordEditText.getText().toString().trim();

        if (!emailEntry.isEmpty() && !passwordEntry.isEmpty()) {
            loginViewModel.signInWithEmail(emailEntry, passwordEntry).observe(this, user -> {
                if (user.isPresent()) {
                    navigateToMainActivity();
                } else {
                    Toast.makeText(getActivity(), "Invalid username and password.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please complete entries.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void navigateToAccountCreation() {
        Fragment newFragment = new CreateAccountFragment();
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(R.id.login_root_container, newFragment).commit();
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
