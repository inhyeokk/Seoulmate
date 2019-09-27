package com.soksok.seoulmate.view.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.databinding.ActivitySettingBinding;
import com.soksok.seoulmate.view.like.LikeActivity;
import com.soksok.seoulmate.view.login.LoginActivity;

public class SettingActivity extends AppCompatActivity {

    public static final String EXTRA_LIKE = "EXTRA_LIKE";
    public static final String VALUE_LIKE_MATE = "VALUE_LIKE_MATE";
    public static final String VALUE_LIKE_SPOT = "VALUE_LIKE_SPOT";
    public static final String VALUE_LIKE_RESTAURANT = "VALUE_LIKE_RESTAURANT";

    private ActivitySettingBinding binding;

    private BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();

    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
    }

    private void setupViews() {

        behavior = BottomSheetBehavior.from(binding.clBottomSheet);
        setBottomClickable(false);
    }

    /*
     * 클릭 이벤트
     */
    public void onLayoutClick(View v) {
        setBottomClickable(false);
    }

    public void onCertifyClick(View v) {
        setBottomClickable(true);
    }

    public void onLikeMateClick(View v) {
        goToLikeActivity(VALUE_LIKE_MATE);
    }

    public void onLikeSpotClick(View v) {
        goToLikeActivity(VALUE_LIKE_SPOT);
    }

    public void onLikeRestaurantClick(View v) {
        goToLikeActivity(VALUE_LIKE_RESTAURANT);
    }

    public void onLogoutClick(View v) {
        showLogoutDialog();
    }

    public void onBottomCancelClick(View v) {
        setBottomClickable(false);
    }

    public void onBottomContactClick(View v) {
        setBottomClickable(false);
    }

    private void setBottomClickable(boolean isClickable) {

        if (isClickable) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            binding.tvBottomCancel.setClickable(true);
            binding.tvBottomCancel.setFocusable(true);
            binding.tvBottomContact.setClickable(true);
            binding.tvBottomContact.setFocusable(true);
            binding.clBackground.setVisibility(View.VISIBLE);
            binding.clBackground.setClickable(true);
            binding.clBackground.setFocusable(true);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            binding.tvBottomCancel.setClickable(false);
            binding.tvBottomCancel.setFocusable(false);
            binding.tvBottomContact.setClickable(false);
            binding.tvBottomContact.setFocusable(false);
            binding.clBackground.setVisibility(View.GONE);
            binding.clBackground.setClickable(false);
            binding.clBackground.setFocusable(false);
        }
    }

    private void goToLikeActivity(String value) {

        Intent intent = new Intent(this, LikeActivity.class);
        intent.putExtra(EXTRA_LIKE, value);
        startActivity(intent);
    }

    private void goToLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showLogoutDialog() {

        LogoutDialog dialog = new LogoutDialog(this);
        dialog.show();

        dialog.isLogout.observe(this, isLogout -> {
            if (isLogout) {
                /**
                 * 토큰 폐기 후 로그아웃
                 */
                PrefUtils.setToken("");
                goToLoginActivity();
            } else {
                // do nothing
            }
        });
    }
}
