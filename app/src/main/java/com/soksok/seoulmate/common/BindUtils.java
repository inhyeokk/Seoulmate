package com.soksok.seoulmate.common;

import android.content.Context;
import android.content.res.Resources;

import com.soksok.seoulmate.R;

import org.jetbrains.annotations.NotNull;

public class BindUtils {

    private static Resources resources;

    public static void init(@NotNull Context context) {
        resources = context.getResources();
    }

    public static String setMainTitle(String name) {
        return resources.getString(R.string.main_tv_title, name);
    }
}
