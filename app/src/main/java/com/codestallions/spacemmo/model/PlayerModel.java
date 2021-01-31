package com.codestallions.spacemmo.model;

import com.google.firebase.firestore.DocumentReference;

public class PlayerModel {

    private String playerId;

    private String email;

    private String displayName;

    private boolean isVerified;

    private DocumentReference location;

    public PlayerModel() {
    }

    /** Used for initial profile creation **/
    public PlayerModel(String playerId, String email) {
        this.playerId = playerId;
        this.email = email;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
