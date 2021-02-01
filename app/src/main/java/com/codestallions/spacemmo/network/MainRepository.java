package com.codestallions.spacemmo.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.codestallions.spacemmo.SpaceMMO;
import com.codestallions.spacemmo.model.DestinationModel;
import com.codestallions.spacemmo.model.PlanetModel;
import com.codestallions.spacemmo.model.PlayerModel;
import com.codestallions.spacemmo.model.RootDestinationsModel;
import com.codestallions.spacemmo.model.StarSystemModel;
import com.codestallions.spacemmo.model.StarsModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

public class MainRepository {

    private static final String TAG = MainRepository.class.getSimpleName();

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public MutableLiveData<List<PlanetModel>> retrieveLocalPlanets(String currentSystem) {
        MutableLiveData<List<PlanetModel>> result = new MutableLiveData<>();
        FirebaseFirestore.getInstance()
                .collection("starmap")
                .document("stars").addSnapshotListener((value, error) -> {
                    if (error != null ) {
                        Log.e(TAG, error.toString());
                    }

                    if (value != null && value.exists()) {
                        StarsModel stars = value.toObject(StarsModel.class);

                        for (StarSystemModel system : stars.getSystems()) {
                            if (system.getName().equals(currentSystem)) {
                                result.setValue(system.getPlanets());
                            }
                        }
                        //To Do: Handle planet match not found
                    }
                });
        return result;
    }

    public MutableLiveData<List<DestinationModel>> retrievePlanetDestinations(DocumentReference destinationsRef) {
        MutableLiveData<List<DestinationModel>> result = new MutableLiveData<>();
        if (destinationsRef != null) {
            destinationsRef.addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.e(TAG, error.toString());
                }

                if (value != null && value.exists()) {
                    RootDestinationsModel destinationsModel = value.toObject(RootDestinationsModel.class);
                    result.setValue(destinationsModel.getDestinations());
                }
            });
        }
        return result;
    }

    public MutableLiveData<PlayerModel> retrievePlayerData() {
        MutableLiveData<PlayerModel> result = new MutableLiveData<>();
        FirebaseFirestore.getInstance()
                .collection("players")
                .document(SpaceMMO.getAuth().getUid())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e(TAG, error.toString());
                    }

                    if (value != null && value.exists()) {
                        PlayerModel playerModel = value.toObject(PlayerModel.class);
                        result.setValue(playerModel);
                    }
                });
        return result;
    }

    private static class SingletonHolder {
        private static final MainRepository INSTANCE = new MainRepository();
    }
}
