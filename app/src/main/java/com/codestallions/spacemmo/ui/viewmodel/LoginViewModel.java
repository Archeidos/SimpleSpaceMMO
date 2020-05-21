package com.codestallions.spacemmo.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codestallions.spacemmo.model.User;
import com.codestallions.spacemmo.network.LoginRepository;
import com.google.common.base.Optional;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
    }

    public MutableLiveData<Optional<User>> signInWithEmail(String email, String password) {
        return loginRepository.firebaseSignInWithEmail(email, password);
    }
    public MutableLiveData<Optional<User>> signInWithEmailEx(Context context, String email, String password) {
        return loginRepository.firebaseSignInWithEmail(email, password);
    }

    public MutableLiveData<Optional<User>> createAccountWithEmail(String email, String password) {
        return loginRepository.firebaseCreateAccountWithEmail(email, password);
    }
}
