package com.soksok.seoulmate.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class BasicUtils {

    private static WindowManager windowManager;

    public static void init(@NotNull Context context) {
        windowManager = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @NotNull
    public static String[] split(@NotNull String string, String regex) {
        return string.split(regex);
    }

    public static int dpToPx(int dp) {

        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return Math.round(dp * dm.density);
    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0] - dpToPx(150);
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }
}
