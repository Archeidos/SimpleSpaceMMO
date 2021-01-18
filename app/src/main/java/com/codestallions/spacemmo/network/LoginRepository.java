package com.codestallions.spacemmo.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.codestallions.spacemmo.SpaceMMO;
import com.codestallions.spacemmo.model.AuthUserModel;
import com.codestallions.spacemmo.model.PlayerModel;
import com.google.common.base.Optional;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();

    private LoginRepository() {
    }

    public static LoginRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public MutableLiveData<Optional<AuthUserModel>> firebaseSignInWithEmail(String email, String password) {
        MutableLiveData<Optional<AuthUserModel>> authenticatedUserMutableLiveData = new MutableLiveData<>();
        SpaceMMO.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = SpaceMMO.getAuth().getCurrentUser();
                String uid = firebaseUser.getUid();
                String name = firebaseUser.getDisplayName();
                AuthUserModel user = new AuthUserModel(uid, name, email);
                authenticatedUserMutableLiveData.setValue(Optional.of(user));
            } else {
                Log.d(TAG, "sign-inUserWithEmail:failure", task.getException());
                authenticatedUserMutableLiveData.setValue(Optional.absent());
            }
        });
        return authenticatedUserMutableLiveData;
    }

    public MutableLiveData<Optional<AuthUserModel>> firebaseCreateAccountWithEmail(String email, String password) {
        MutableLiveData<Optional<AuthUserModel>> createdUserMutableLiveData = new MutableLiveData<>();
        SpaceMMO.getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = SpaceMMO.getAuth().getCurrentUser();
                AuthUserModel user = new AuthUserModel(firebaseUser.getUid(), firebaseUser.getDisplayName(), email);
                createdUserMutableLiveData.setValue(Optional.of(user));
            } else {
                createdUserMutableLiveData.setValue(Optional.absent());
            }
        });
        return createdUserMutableLiveData;
    }

    public void firebasePostNewPlayerProfile(PlayerModel playerModel) {
        FirebaseFirestore.getInstance()
                .collection("players")
                .document(playerModel.getPlayerId())
                .set(playerModel).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                    } else {

                    }
                });
    }

    private static class SingletonHolder {
        private static final LoginRepository INSTANCE = new LoginRepository();
    }
}
