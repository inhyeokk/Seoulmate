package com.soksok.seoulmate.view.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

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

    }

    /*
     * 클릭 이벤트
     */
    public void onCertifyClick(View v) {

    }

    public void onLikeMateClick(View v) {

    }

    public void onLikeSpotClick(View v) {

    }

    public void onLikeRestaurantClick(View v) {

    }

    public void onLogoutClick(View v) {

    }
}
