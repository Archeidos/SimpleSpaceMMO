package com.codestallions.spacemmo.model;


public class PlayerModel {

    private String playerId;

    private String email;

    private String displayName;

    private boolean verified;

    private String locationRef;

    private String currentCoords;

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
        return verified;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLocationRef() {
        return locationRef;
    }

    public String getCurrentCoords() {
        return currentCoords;
    }
}
