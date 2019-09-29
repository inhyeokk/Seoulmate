package com.soksok.seoulmate.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.http.model.User;
import com.soksok.seoulmate.view.chat.entity.ChatItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PrefUtils {

    private static final String LOGIN_TOKEN = "LOGIN_TOKEN";
    private static final String CHAT_ITEMS = "CHAT_ITEMS";

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

    public static ArrayList<ChatItem> getChatItems(@NotNull User user, String index) {

        String chatItemsString;
        try {
            chatItemsString = preferences.getString(
                    resources.getString(
                            R.string.common_pref_chat_item,
                            CHAT_ITEMS,
                            user.getEmail(),
                            index
                    ),
                    ""
            );
        } catch (NullPointerException e) {
            chatItemsString = "";
        }

        ArrayList<ChatItem> chatItems;
        try {
            Gson gson = new Gson();
            chatItems = gson.fromJson(chatItemsString, new TypeToken<ArrayList<ChatItem>>(){}.getType());
        } catch (Exception e) {
            chatItems = new ArrayList<>();
        }
        return chatItems;
    }

    public static void setChatItems(@NotNull User user, String index, ArrayList<ChatItem> chatItems) {

        Gson gson = new Gson();
        String chatItemString = gson.toJson(chatItems);

        preferences.edit()
                .putString(
                        resources.getString(
                        R.string.common_pref_chat_item,
                        CHAT_ITEMS,
                        user.getEmail(),
                        index
                ), chatItemString)
                .apply();
    }
}
