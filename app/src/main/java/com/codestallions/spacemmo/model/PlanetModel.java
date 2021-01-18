package com.codestallions.spacemmo.model;

import com.google.firebase.firestore.DocumentReference;

public class PlanetModel {

    private String name;

    private String imagePath;

    private DocumentReference destinationsRef;

    public PlanetModel() {
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public DocumentReference getDestinationsRef() {
        return destinationsRef;
    }
}
