package com.codestallions.spacemmo.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codestallions.spacemmo.model.AuthUserModel;
import com.codestallions.spacemmo.model.PlayerModel;
import com.codestallions.spacemmo.network.LoginRepository;
import com.google.common.base.Optional;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
    }

    public MutableLiveData<Optional<AuthUserModel>> signInWithEmail(String email, String password) {
        return loginRepository.firebaseSignInWithEmail(email, password);
    }

    public MutableLiveData<Optional<PlayerModel>> createAccountWithEmail(String email, String password) {
        return loginRepository.firebaseCreateAccountWithEmail(email, password);
    }

    public MutableLiveData<Boolean> sendVerificationEmail(FirebaseUser user) {
        return loginRepository.firebaseSendVerificationEmail(user);
    }

    public void postNewPlayerProfile(PlayerModel playerModel) {
        loginRepository.firebasePostNewPlayerProfile(playerModel);
    }

    public MutableLiveData<Boolean> handleLoginRequirements(String playerId) {
        return loginRepository.updatePlayerForFirstLogin(playerId);
    }
}
