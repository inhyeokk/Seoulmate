package com.soksok.seoulmate.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.view.setting.SettingActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BindUtils {

    private static Resources resources;

    public static void init(@NotNull Context context) {
        resources = context.getResources();
    }

    @NotNull
    public static String setMainTitle(String name) {
        return resources.getString(R.string.main_tv_title, name);
    }

    @NotNull
    public static String setTourDate(String startDate, String endDate) {

        ArrayList<Integer> startDateTime = BasicUtils.getDateTime(startDate);
        ArrayList<Integer> endDateTime = BasicUtils.getDateTime(endDate);

        String startTime = "AM";
        if (startDateTime.get(3) >= 12) {
            int hour = startDateTime.get(3);
            hour -= 12;
            startDateTime.remove(3);
            startDateTime.add(3, hour);
            startTime = "PM";
        }
        String endTime = "AM";
        if (endDateTime.get(3) >= 12) {
            int hour = endDateTime.get(3);
            hour -= 12;
            endDateTime.remove(3);
            endDateTime.add(3, hour);
            endTime = "PM";
        }

        // 첫째날 마지막날 동일한 경우
        if (startDateTime.get(0).equals(endDateTime.get(0))
                && startDateTime.get(1).equals(endDateTime.get(1))
                && startDateTime.get(2).equals(endDateTime.get(2))) {

            return resources.getString(R.string.my_trip_tv_date_short,
                    startDateTime.get(1), // 월
                    startDateTime.get(2), // 일
                    startDateTime.get(3),
                    startTime,
                    endDateTime.get(3),
                    endTime);
        } else { // 동일하지 않은 경우

            return resources.getString(R.string.my_trip_tv_date_long,
                    startDateTime.get(1), // 월
                    startDateTime.get(2), // 일
                    startDateTime.get(3),
                    startTime,
                    endDateTime.get(1), // 월
                    endDateTime.get(2), // 일
                    endDateTime.get(3),
                    endTime);
        }
    }

    public static String setTourMember(int adult, int children, int baby) {
        String member = "";
        if (adult > 0) {
            member += resources.getString(R.string.my_trip_tv_member_adult, adult);
        }
        if (children > 0) {
            if (!member.equals("")) {
                member += " ";
            }
            member += resources.getString(R.string.my_trip_tv_member_children, children);
        }
        if (baby > 0) {
            if (!member.equals("")) {
                member += " ";
            }
            member += resources.getString(R.string.my_trip_tv_member_baby, baby);
        }

        // 멤버가 아무도 없다면 "성인 1명" 반환
        if (member.equals("")) {
            member += resources.getString(R.string.my_trip_tv_member_adult, 1);
        }

        return member;
    }

    @NotNull
    public static String setWithMate(@NotNull String mateEmail) {

        String mate = "다은";
        switch (mateEmail) {
            case "mate0@korea.com":
                mate = "다은";
                break;
            case "mate1@korea.com":
                mate = "수연";
                break;
            case "mate2@korea.com":
                mate = "은미";
                break;
            case "mate3@korea.com":
                mate = "형규";
                break;
            case "mate4@korea.com":
                mate = "원서";
                break;
            case "mate5@korea.com":
                mate = "창윤";
                break;
            case "mate6@korea.com":
                mate = "은지";
                break;
            case "mate7@korea.com":
                mate = "용준";
                break;
            case "mate8@korea.com":
                mate = "예린";
                break;
            case "mate9@korea.com":
                mate = "유정";
                break;
        }
        return resources.getString(R.string.my_trip_tv_with_mate, mate);
    }

    public static String setLikeTitle(@NotNull String value) {

        String result = "";
        switch (value) {
            case SettingActivity.VALUE_LIKE_MATE:
                result = resources.getString(R.string.setting_like_mate);
                break;

            case SettingActivity.VALUE_LIKE_SPOT:
                result = resources.getString(R.string.setting_like_spot);
                break;

            case SettingActivity.VALUE_LIKE_RESTAURANT:
                result = resources.getString(R.string.setting_like_restaurant);
                break;
        }
        return result;
    }

    public static void setLikeItemTitle(TextView textView, @NotNull String value, String title) {

        switch (value) {
            case SettingActivity.VALUE_LIKE_MATE:
                String string = resources.getString(R.string.like_tv_mate, title);
                Spannable spannable = new SpannableString(string);
                spannable.setSpan(
                        new ForegroundColorSpan(resources.getColor(R.color.colorPrimary, null)),
                        string.length() - title.length(),
                        string.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                textView.setText(spannable);
                break;

            case SettingActivity.VALUE_LIKE_SPOT:
                textView.setText(title);
                break;

            case SettingActivity.VALUE_LIKE_RESTAURANT:
                textView.setText(title);
                break;
        }
    }

    public static void setGalleryURI(@NotNull ImageView view, Uri uri) {

        Glide.with(view.getContext())
                .load(uri)
                .into(view);
    }

    public static void setCircleGalleryURI(@NotNull ImageView view, Uri uri) {

        Glide.with(view.getContext())
                .load(uri)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    @BindingAdapter("bind_image_drawable")
    public static void setImageDrawable(@NotNull ImageView view, int drawableId) {

        Drawable drawable = resources.getDrawable(drawableId, null);
        Glide.with(view.getContext())
                .load(drawable)
                .into(view);
    }

    @BindingAdapter("bind_image_base64")
    public static void setImageBase64(@NotNull ImageView view, String encodedImage) {

        Bitmap bitmap = BasicUtils.fromBase64(encodedImage);
        Glide.with(view.getContext())
                .load(bitmap)
                .into(view);
    }

    public static void setImageMateProfile(@NotNull ImageView view, @NotNull String mateEmail) {

        int drawableId = R.drawable.ic_mate_profile2;
        switch (mateEmail) {
            case "mate0@korea.com":
                drawableId = R.drawable.ic_mate_profile2;
                break;
            case "mate1@korea.com":
                drawableId = R.drawable.ic_mate_profile3;
                break;
            case "mate2@korea.com":
                drawableId = R.drawable.ic_mate_profile4;
                break;
            case "mate3@korea.com":
                drawableId = R.drawable.ic_mate_profile5;
                break;
            case "mate4@korea.com":
                drawableId = R.drawable.ic_mate_profile6;
                break;
            case "mate5@korea.com":
                drawableId = R.drawable.ic_mate_profile7;
                break;
            case "mate6@korea.com":
                drawableId = R.drawable.ic_mate_profile8;
                break;
            case "mate7@korea.com":
                drawableId = R.drawable.ic_mate_profile9;
                break;
            case "mate8@korea.com":
                drawableId = R.drawable.ic_mate_profile10;
                break;
            case "mate9@korea.com":
                drawableId = R.drawable.ic_mate_profile11;
                break;
        }
        Drawable drawable = resources.getDrawable(drawableId, null);
        Glide.with(view.getContext())
                .load(drawable)
                .into(view);
    }

    public static void setImageMate(@NotNull ImageView view, @NotNull int position) {

        int drawableId = R.drawable.ic_mate2;
        switch (position) {
            case 0:
                drawableId = R.drawable.ic_mate2;
                break;
            case 1:
                drawableId = R.drawable.ic_mate3;
                break;
            case 2:
                drawableId = R.drawable.ic_mate4;
                break;
            case 3:
                drawableId = R.drawable.ic_mate5;
                break;
            case 4:
                drawableId = R.drawable.ic_mate6;
                break;
            case 5:
                drawableId = R.drawable.ic_mate7;
                break;
            case 6:
                drawableId = R.drawable.ic_mate8;
                break;
            case 7:
                drawableId = R.drawable.ic_mate9;
                break;
            case 8:
                drawableId = R.drawable.ic_mate10;
                break;
            case 9:
                drawableId = R.drawable.ic_mate11;
                break;
        }
        Drawable drawable = resources.getDrawable(drawableId, null);
        Glide.with(view.getContext())
                .load(drawable)
                .into(view);
    }
}
