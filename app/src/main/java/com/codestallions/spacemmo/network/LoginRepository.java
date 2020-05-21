package com.codestallions.spacemmo.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.codestallions.spacemmo.SpaceMMO;
import com.codestallions.spacemmo.model.User;
import com.google.common.base.Optional;
import com.google.firebase.auth.FirebaseUser;


public class LoginRepository {

    private static final String TAG = "LoginRepository";

    private LoginRepository() {
    }

    public static LoginRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public MutableLiveData<Optional<User>> firebaseSignInWithEmail(String email, String password) {
        MutableLiveData<Optional<User>> authenticatedUserMutableLiveData = new MutableLiveData<>();
        SpaceMMO.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = SpaceMMO.getAuth().getCurrentUser();
                String uid = firebaseUser.getUid();
                String name = firebaseUser.getDisplayName();
                User user = new User(uid, name, email);
                authenticatedUserMutableLiveData.setValue(Optional.of(user));
            } else {
                Log.d(TAG, "sign-inUserWithEmail:failure", task.getException());
                authenticatedUserMutableLiveData.setValue(Optional.absent());
            }
        });
        return authenticatedUserMutableLiveData;
    }

    public MutableLiveData<Optional<User>> firebaseCreateAccountWithEmail(String email, String password) {
        MutableLiveData<Optional<User>> createdUserMutableLiveData = new MutableLiveData<>();
        SpaceMMO.getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = SpaceMMO.getAuth().getCurrentUser();
                User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), email);
                createdUserMutableLiveData.setValue(Optional.of(user));
            } else {
                createdUserMutableLiveData.setValue(Optional.absent());
            }
        });
        return createdUserMutableLiveData;
    }

    private static class SingletonHolder {
        private static final LoginRepository INSTANCE = new LoginRepository();
    }
}
