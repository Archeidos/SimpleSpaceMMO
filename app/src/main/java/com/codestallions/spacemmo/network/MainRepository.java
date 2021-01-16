package com.codestallions.spacemmo.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.codestallions.spacemmo.model.PlanetModel;
import com.codestallions.spacemmo.model.StarsModel;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

public class MainRepository {

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public MutableLiveData<List<PlanetModel>> retrieveLocalPlanets() {
        MutableLiveData<List<PlanetModel>> result = new MutableLiveData<>();
        FirebaseFirestore.getInstance()
                .collection("starmap")
                .document("stars").addSnapshotListener((value, error) -> {
                    if (error != null ) {
                        Log.e("", "");
                    }

                    if (value != null && value.exists()) {
                        StarsModel stars = value.toObject(StarsModel.class);
                        result.setValue(stars.getSystems().get(0).getPlanets());
                    }
                });
        return result;
    }

    private static class SingletonHolder {
        private static final MainRepository INSTANCE = new MainRepository();
    }
}
