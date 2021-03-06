package com.codestallions.spacemmo;

import android.content.Context;
import android.util.Log;

import com.codestallions.spacemmo.model.PlayerModel;
import com.codestallions.spacemmo.util.SharedPrefUtil;

public class SessionManager {

    private static SessionManager INSTANCE = null;
    private static PlayerModel playerModel;

    private static final String LOGIN_EXPIRATION_TIME_STAMP = "login_expiration_timestamp";

    private SessionManager() {
    }

    public static SessionManager getInstance() {
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

    public void cachePlayerModel(PlayerModel playerModel) {
        SessionManager.playerModel = playerModel;
    }

    public boolean isLoginExpired(Context context) {
        return System.currentTimeMillis() / 1000L >= SharedPrefUtil.getLongData(context, LOGIN_EXPIRATION_TIME_STAMP);
    }
}
