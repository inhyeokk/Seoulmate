package com.soksok.seoulmate.common;

import android.content.Context;
import android.content.res.Resources;
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

public class BindUtils {

    private static Resources resources;

    public static void init(@NotNull Context context) {
        resources = context.getResources();
    }

    @NotNull
    public static String setMainTitle(String name) {
        return resources.getString(R.string.main_tv_title, name);
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

    @BindingAdapter("bind_mate_drawable")
    public static void setMateDrawable(@NotNull ImageView view, int drawableId) {

        Drawable drawable = resources.getDrawable(drawableId, null);
        Glide.with(view.getContext())
                .load(drawable)
                .into(view);
    }
}
