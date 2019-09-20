package com.soksok.seoulmate.view.like;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ActivityLikeBinding;
import com.soksok.seoulmate.view.like.adapter.LikeAdapter;
import com.soksok.seoulmate.view.setting.SettingActivity;

public class LikeActivity extends AppCompatActivity {

    private ActivityLikeBinding binding;

    private LikeAdapter likeAdapter;

    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        onDataBinding();
        setupViews();
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_like);
        binding.setValue(value);
        binding.executePendingBindings();
    }

    private void getData() {
        value = getIntent().getStringExtra(SettingActivity.EXTRA_LIKE);
    }

    private void setupViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvLike.setLayoutManager(layoutManager);
        likeAdapter = new LikeAdapter(value);
        binding.rcvLike.setAdapter(likeAdapter);

        // TODO set adapter item
        switch (value) {
            case SettingActivity.VALUE_LIKE_MATE:

                break;

            case SettingActivity.VALUE_LIKE_SPOT:

                break;

            case SettingActivity.VALUE_LIKE_RESTAURANT:

                break;
        }
    }
}
