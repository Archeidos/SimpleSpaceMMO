package com.codestallions.spacemmo;

import android.content.Context;
import android.util.Log;

import com.codestallions.spacemmo.model.PlayerModel;
import com.codestallions.spacemmo.util.SharedPrefUtil;

public class SessionManager {
    private static final String TAG = SessionManager.class.getSimpleName();
    private static final String LOGIN_EXPIRATION_TIME_STAMP = "login_expiration_timestamp";
    private static final int CURRENT_SYSTEM = 3;
    private static final int CURRENT_PLANET = 4;
    private static final int CURRENT_LOCAL = 5;

    private static SessionManager INSTANCE = null;
    private static PlayerModel playerModel;
    private static String[] playerLocationSegments;

    private SessionManager() {
    }

    protected static SessionManager getInstance() {
        return INSTANCE == null ? new SessionManager() : INSTANCE;
    }

    public void saveLoginExpirationTime(Context context) {
        SpaceMMO.getAuth().getAccessToken(true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                SharedPrefUtil.saveData(context, LOGIN_EXPIRATION_TIME_STAMP, task.getResult().getExpirationTimestamp());
            } else {
                Log.e("", "");
            }
        });
    }

    public void cachePlayerData(PlayerModel playerModel) {
        SessionManager.playerModel = playerModel;
        cacheLocationSegments();
    }

    public String getPlayerSystemLocation() {
        return playerLocationSegments[CURRENT_SYSTEM];
    }

    public String getPlayerPlanetLocation() {
        return playerLocationSegments[CURRENT_PLANET];
    }

    public String getPlayerCoordinates() {
        return playerModel.getCurrentCoords();
    }

    public boolean isLoginExpired(Context context) {
        return System.currentTimeMillis() / 1000L >= SharedPrefUtil.getLongData(context, LOGIN_EXPIRATION_TIME_STAMP);
    }

    private void cacheLocationSegments() {
        playerLocationSegments = SessionManager.playerModel.getLocationRef().split("/");
    }
}
