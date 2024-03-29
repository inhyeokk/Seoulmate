package com.soksok.seoulmate.common;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.soksok.seoulmate.R;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;

public class BasicUtils {

    private static ContentResolver contentResolver;
    private static InputMethodManager inputMethodManager;
    private static RequestManager requestManager;
    private static Resources resources;
    private static WindowManager windowManager;

    public static void init(@NotNull Context context) {
        contentResolver = context.getContentResolver();
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        requestManager = Glide.with(context);
        resources = context.getResources();
        windowManager = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void onCloseKeyboard(@NotNull View v) {
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static int getRandomValue(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }

    public static ArrayList<Integer> getDateTime(String date) {

        System.out.println("getDateTime : " + date);

        String[] dateTimeSplit = split(date, " ");
        for(int i=0 ; i<dateTimeSplit.length ; i++){
            System.out.println("dateTimeSplit : " + dateTimeSplit[i]);
        }

        String dateString = dateTimeSplit[0]; // date "yyyy.MM.dd"
        String timeString = dateTimeSplit[1]; // time "hh:MM"

        String[] dateSplit = split(dateString, "\\-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);

        String[] timeSplit = split(timeString, "\\:");
        int hour = Integer.parseInt(timeSplit[0]);
        int minute = Integer.parseInt(timeSplit[1]);

        ArrayList<Integer> dateTime = new ArrayList<>();
        dateTime.add(year);
        dateTime.add(month);
        dateTime.add(day);
        dateTime.add(hour);
        dateTime.add(minute);
        return dateTime;
    }

    public static ArrayList<Integer> getDate() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayList<Integer> date = new ArrayList<>();
        date.add(year); // date.get(0)
        date.add(month);// date.get(1)
        date.add(day);  // date.get(2)
        return date;
    }

    @NotNull
    public static String getTime() {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String hourr = String.valueOf(hour);
        if (hour / 10 == 0) {
            hourr = "0" + hour;
        }

        String minutee = String.valueOf(minute);
        if (minute / 10 == 0) {
            minutee = "0" + minute;
        }

        return resources.getString(R.string.match_tv_time, hourr, minutee);
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

    // 비트맵 이미지 캐싱
    @NotNull
    public static Observable<String> getImageCachePathFromURI(Uri uri) {

        return Observable.fromCallable(() -> {
            String path = "";
            try {
                File file = requestManager.asFile()
                        .load(uri)
                        .submit().get();
                path = file.getPath();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return path;
        });
    }

    public static String toBase64(@NotNull ImageView iv) {

        BitmapDrawable bitmapDrawable = (BitmapDrawable) iv.getDrawable();
        Bitmap tempBitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        tempBitmap.compress(Bitmap.CompressFormat.JPEG,70,bos);
        byte[] data = bos.toByteArray();
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static String fromURIToBase64(Uri uri) {

        Bitmap tempBitmap;
        String result = "";
        try {
            tempBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            tempBitmap.compress(Bitmap.CompressFormat.JPEG,70,bos);
            byte[] data = bos.toByteArray();
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap fromBase64(String encodedImage) {

        byte[] decodedByte = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static void getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
                System.out.println("Hash -> " + keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
        if (keyHash != null) {
            Log.d("KEY_HASH", keyHash);
            System.out.println("Hash -> " + keyHash);
        }
    }
}
