package com.soksok.seoulmate.view.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

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

    }

    public void onLikeSpotClick(View v) {

    }

    public void onLikeRestaurantClick(View v) {

    }

    public void onLogoutClick(View v) {

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
}
