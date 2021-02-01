package com.codestallions.spacemmo.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.SessionManager;
import com.codestallions.spacemmo.SpaceMMO;
import com.codestallions.spacemmo.databinding.FragmentLoginBinding;
import com.codestallions.spacemmo.ui.activities.MainActivity;
import com.codestallions.spacemmo.ui.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements ILoginFragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    public static final String CREATE_TAG = "CREATE_TAG";

    private FragmentLoginBinding loginBinding;

    private LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);
        loginBinding.setLoginViewModel(loginViewModel);
        loginBinding.setILoginFragment(this);

        handleLoginNavigation();
        return loginBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void handleLoginEntry() {
        String emailEntry = loginBinding.loginEmailEntry.getText().toString().trim();
        String passwordEntry = loginBinding.loginPasswordEntry.getText().toString().trim();

        if (!emailEntry.isEmpty() && !passwordEntry.isEmpty()) {
            loginViewModel.signInWithEmail(emailEntry, passwordEntry).observe(this, user -> {
                if (user.isPresent() && isUserSignedInValid()) {
                    handleLoginNavigation();
                } else if (user.isPresent() && !isUserSignedInValid()) {
                    Toast.makeText(getActivity(), "Please verify your email address to sign in.", Toast.LENGTH_LONG).show();
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
        getParentFragmentManager().beginTransaction().replace(R.id.login_root_container, newFragment).addToBackStack(CREATE_TAG).commit();
    }

    private void handleLoginNavigation() {
        // Query player profile in DB, update 'verified' to true and place player in world if it was false.
        if (isUserSignedInValid()) {
            loginViewModel.handleLoginRequirements(SpaceMMO.getAuth().getCurrentUser().getUid())
                    .observe(getViewLifecycleOwner(), isSuccessful -> {
                        if (isSuccessful) {
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(getContext(), "Something went wrong. Please try again later.", Toast.LENGTH_LONG).show();
                        }
            });
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SpaceMMO.getSession().saveLoginExpirationTime(getContext());
        startActivity(intent);
    }

    private boolean isUserSignedInValid() {
        FirebaseUser user = SpaceMMO.getAuth().getCurrentUser();
        return user != null && user.isEmailVerified();
    }

}
