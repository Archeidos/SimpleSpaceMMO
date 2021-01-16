package com.codestallions.spacemmo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.databinding.FragmentCreateAccountBinding;
import com.codestallions.spacemmo.ui.viewmodel.LoginViewModel;
import com.codestallions.spacemmo.util.StringUtil;

public class CreateAccountFragment extends Fragment implements ICreateAccountFragment {

    private LoginViewModel loginViewModel;

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    private String emailEntry;
    private String passwordEntry;
    private String confirmPasswordEntry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCreateAccountBinding accountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false);
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);
        accountBinding.setLoginViewModel(loginViewModel);
        accountBinding.setICreateAccountFragment(this);

        emailEditText = accountBinding.createAccountEmailEntry;
        passwordEditText = accountBinding.createAccountPasswordEntry;
        confirmPasswordEditText = accountBinding.createAccountConfirmPasswordEntry;
        return accountBinding.getRoot();
    }

    @Override
    public void handleAccountCreation() {
        emailEntry = emailEditText.getText().toString().trim();
        passwordEntry = passwordEditText.getText().toString().trim();
        confirmPasswordEntry = confirmPasswordEditText.getText().toString().trim();

        if (StringUtil.isTextEntered(emailEntry, passwordEntry, confirmPasswordEntry)) {
            if (passwordEntry.equals(confirmPasswordEntry)) {
                loginViewModel.createAccountWithEmail(emailEntry, passwordEntry);
            }
        }
    }

}
