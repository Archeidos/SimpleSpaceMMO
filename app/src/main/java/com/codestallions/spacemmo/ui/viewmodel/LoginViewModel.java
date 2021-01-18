package com.codestallions.spacemmo.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codestallions.spacemmo.model.AuthUserModel;
import com.codestallions.spacemmo.model.PlayerModel;
import com.codestallions.spacemmo.network.LoginRepository;
import com.google.common.base.Optional;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
    }

    public MutableLiveData<Optional<AuthUserModel>> signInWithEmail(String email, String password) {
        return loginRepository.firebaseSignInWithEmail(email, password);
    }

    public MutableLiveData<Optional<AuthUserModel>> createAccountWithEmail(String email, String password) {
        return loginRepository.firebaseCreateAccountWithEmail(email, password);
    }

    public void postNewPlayerProfile(PlayerModel playerModel) {
        loginRepository.firebasePostNewPlayerProfile(playerModel);
    }
}
