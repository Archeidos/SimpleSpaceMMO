package com.codestallions.spacemmo.model;

import com.google.firebase.firestore.DocumentReference;

public class PlayerModel {

    private String playerId;

    private String name;

    private DocumentReference location;

    public PlayerModel() {
    }

    /** Used for initial profile creation **/
    public PlayerModel(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;

    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

}
