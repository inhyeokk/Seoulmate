package com.soksok.seoulmate.view.match;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ActivitySelectBinding;

public class SelectActivity extends AppCompatActivity {

    private static final int REQUEST_SPOT_LIST = 4001;

    /** MatchActivity 로부터 받은 데이터
     *  메이트 신청 탭까지 넘겨야함
     */
    private String firstDateString;
    private String lastDateString;
    private int adultCount;
    private int childCount;
    private int babyCount;

    private ActivitySelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        onDataBinding();
        setupViews();
    }

    private void getData() {

        Intent intent = getIntent();
        firstDateString = intent.getStringExtra(MatchActivity.EXTRA_FIRST_DATE);
        lastDateString = intent.getStringExtra(MatchActivity.EXTRA_LAST_DATE);
        adultCount = intent.getIntExtra(MatchActivity.EXTRA_ADULT_COUNT, 0);
        childCount = intent.getIntExtra(MatchActivity.EXTRA_CHILD_COUNT, 0);
        babyCount = intent.getIntExtra(MatchActivity.EXTRA_BABY_COUNT, 0);
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select);
    }

    private void setupViews() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SPOT_LIST:
                switch (resultCode) {
                    case RESULT_OK:
                        binding.tvSpotShowList.setSelected(true);
                        binding.tvSpotShowList.setText(R.string.spot_list_tv_complete);
                        break;

                    case RESULT_CANCELED:
                        binding.tvSpotShowList.setSelected(false);
                        binding.tvSpotShowList.setText(R.string.select_tv_spot_show_list);
                        break;
                }
                break;
        }
    }

    /*
     * 클릭 이벤트
     */
    public void onShowListClick(View v) {
        goToSpotListActivity();
    }

    // 여행 스타일
    public void onStyleRestaurantClick(View v) {
        v.setSelected(!v.isSelected());
    }
    public void onStyleRecreationClick(View v) {
        v.setSelected(!v.isSelected());
    }
    public void onStyleTourismClick(View v) {
        v.setSelected(!v.isSelected());
    }
    public void onStyleActivityClick(View v) {
        v.setSelected(!v.isSelected());
    }
    public void onStyleCultureClick(View v) {
        v.setSelected(!v.isSelected());
    }

    // 여행 유형
    public void onTypeActiveClick(View v) {
        v.setSelected(!v.isSelected());
    }
    public void onTypeChallengingClick(View v) {
        v.setSelected(!v.isSelected());
    }
    public void onTypeFreeClick(View v) {
        v.setSelected(!v.isSelected());
    }
    public void onTypeRelyClick(View v) {
        v.setSelected(!v.isSelected());
    }
    public void onTypeBusyClick(View v) {
        v.setSelected(!v.isSelected());
    }

    public void onBeforeClick(View v) {
        finish();
    }

    public void onCompleteClick(View v) {

    }

    private void goToSpotListActivity() {
        Intent intent = new Intent(this, SpotListActivity.class);
        startActivityForResult(intent, REQUEST_SPOT_LIST);
    }
}
