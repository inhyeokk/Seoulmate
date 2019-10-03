package com.soksok.seoulmate.view.recommend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.databinding.ActivityMateBinding;

public class MateActivity extends AppCompatActivity {

    private String mateEmail = "mate0@korea.com";

    private ActivityMateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        onDataBinding();
        setupViews();
    }

    private void getData() {
        mateEmail = getIntent().getStringExtra(MateFragment.EXTRA_MATE_EMAIL);
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mate);
    }

    private void setupViews() {
        BindUtils.setImageMate(binding.ivMate, mateEmail);
    }
}
