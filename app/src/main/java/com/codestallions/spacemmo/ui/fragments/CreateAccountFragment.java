package com.codestallions.spacemmo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.SpaceMMO;
import com.codestallions.spacemmo.databinding.FragmentCreateAccountBinding;
import com.codestallions.spacemmo.ui.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

import static com.codestallions.spacemmo.util.StringUtil.isTextEntered;

public class CreateAccountFragment extends Fragment implements ICreateAccountFragment {

    private LoginViewModel loginViewModel;
    private FragmentCreateAccountBinding accountBinding;
    private String email;
    private String password;
    private String confirmPassword;
    private String displayName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        accountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false);
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);
        accountBinding.setLoginViewModel(loginViewModel);
        accountBinding.setICreateAccountFragment(this);

        return accountBinding.getRoot();
    }

    @Override
    public void handleAccountCreation() {
        email = accountBinding.createAccountEmailEntry.getText().toString().trim();
        password = accountBinding.createAccountPasswordEntry.getText().toString().trim();
        confirmPassword = accountBinding.createAccountConfirmPasswordEntry.getText().toString().trim();
        displayName = accountBinding.createAccountDisplayNameEntry.getText().toString().trim();

        if (isTextEntered(email, password, confirmPassword, displayName)) {
            if (password.equals(confirmPassword)) {
                loginViewModel.createAccountWithEmail(email, password)
                        .observe(getViewLifecycleOwner(), playerModel -> {
                            if (playerModel.isPresent() && SpaceMMO.getAuth().getCurrentUser() != null) {
                                playerModel.get().setDisplayName(displayName);
                                loginViewModel.postNewPlayerProfile(playerModel.get());
                                sendEmailVerification(SpaceMMO.getAuth().getCurrentUser());
                            }
                });
            }
        }
    }

    private void sendEmailVerification(FirebaseUser user) {
        loginViewModel.sendVerificationEmail(user).observe(getViewLifecycleOwner(), isSuccessful -> {
            if (isSuccessful) {
                Toast.makeText(getContext(), "Verification email has been sent", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
