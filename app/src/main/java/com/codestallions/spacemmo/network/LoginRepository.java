package com.codestallions.spacemmo.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codestallions.spacemmo.SpaceMMO;
import com.codestallions.spacemmo.model.AuthUserModel;
import com.codestallions.spacemmo.model.PlayerModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Optional;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

import kotlin.Result;


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
                boolean verified = firebaseUser.isEmailVerified();
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

    public MutableLiveData<Optional<PlayerModel>> firebaseCreateAccountWithEmail(String email, String password) {
        MutableLiveData<Optional<PlayerModel>> createdUserMutableLiveData = new MutableLiveData<>();
        SpaceMMO.getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = SpaceMMO.getAuth().getCurrentUser();
                PlayerModel playerModel = new PlayerModel(firebaseUser.getUid(), firebaseUser.getEmail());
                createdUserMutableLiveData.setValue(Optional.of(playerModel));
            } else {
                createdUserMutableLiveData.setValue(Optional.absent());
            }
        });
        return createdUserMutableLiveData;
    }

    public MutableLiveData<Boolean> firebaseSendVerificationEmail(FirebaseUser user) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                result.setValue(true);
            } else {
                result.setValue(false);
            }
        });
        return result;
    }

    public void firebasePostNewPlayerProfile(PlayerModel playerModel) {
        FirebaseFirestore.getInstance()
                .collection("players")
                .document(playerModel.getPlayerId())
                .set(playerModel).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Trigger sending verification email
                        Log.d(TAG, "Player added");
                    } else {
                        // Display error message UI
                        Log.d(TAG, "Failed to add player");
                    }
                });
    }

    public MutableLiveData<Boolean> updatePlayerForFirstLogin(String playerId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        FirebaseFirestore.getInstance()
                .collection("players")
                .document(playerId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().getBoolean("verified")) {
                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("verified", true);
                        updatedData.put("locationRef", "/starmap/stars/Stanton/Crusader");


                        FirebaseFirestore.getInstance()
                                .collection("players")
                                .document(playerId)
                                .update(updatedData)
                                .addOnCompleteListener(updateTask -> result.setValue(updateTask.isSuccessful()));
                    } else if (task.isSuccessful() && task.getResult().getBoolean("verified")){
                        result.setValue(true);
                    } else {
                        result.setValue(false);
                    }
                });
        return result;
    }

    private static class SingletonHolder {
        private static final LoginRepository INSTANCE = new LoginRepository();
    }
}
