package com.soksok.seoulmate.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;

public class PrefUtils {

    private static final String LOGIN_TOKEN = "LOGIN_TOKEN";

    private static Resources resources;
    private static SharedPreferences preferences;

    public static void init(@NotNull Context context) {
        resources = context.getResources();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getToken() {
        String token;
        try {
            token = preferences.getString(LOGIN_TOKEN, "");
        } catch (NullPointerException e) {
            token = "";
        }
        return token;
    }

    public static void setToken(String token) {
        preferences.edit()
                .putString(LOGIN_TOKEN, token)
                .apply();
    }
}
