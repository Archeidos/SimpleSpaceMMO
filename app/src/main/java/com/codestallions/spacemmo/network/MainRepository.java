package com.codestallions.spacemmo.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.codestallions.spacemmo.model.Planet;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainRepository {

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public MutableLiveData<List<Planet>> retrieveLocalPlanets() {
        MutableLiveData<List<Planet>> result = new MutableLiveData<>();
        FirebaseFirestore.getInstance()
                .collection("starmap")
                .document("stars")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        Map<String, Object> starsMap = doc.getData();
                        Log.d("", "");
                        HashMap<String, Object> stanton = (HashMap<String, Object>) starsMap.get("Stanton");
                        HashMap<String, Object> planets = (HashMap<String, Object>) stanton.get("planets");
                        List<Planet> planetList = new ArrayList<>();
                        for (Object o : planets.values()) {
                            HashMap<String, Object> map = (HashMap<String, Object>) o;
                            Planet planet = new Planet();
                            planet.setImagePath(map.get("image_path").toString());
                            planet.setName(map.get("name").toString());
                            planetList.add(planet);
                        }
                        result.setValue(planetList);
                        Log.d("", "");
                    } else {

                    }
        });
        return result;
    }

    private static class SingletonHolder {
        private static final MainRepository INSTANCE = new MainRepository();
    }
}
