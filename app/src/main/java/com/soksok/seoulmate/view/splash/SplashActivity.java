package com.soksok.seoulmate.view.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.databinding.ActivitySplashBinding;
import com.soksok.seoulmate.view.login.LoginActivity;
import com.soksok.seoulmate.view.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1500;

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    private void setupViews() {
        new Handler().postDelayed(this::setAutoLogin, SPLASH_TIME_OUT);
    }

    private void setAutoLogin() {

        if (PrefUtils.getToken().equals("")) {
            goToLoginActivity();
        } else {
            goToMainActivity();
        }
    }

    private void goToLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
