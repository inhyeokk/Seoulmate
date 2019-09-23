package com.soksok.seoulmate.view.match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.databinding.ActivitySpotListBinding;
import com.soksok.seoulmate.view.match.adapter.SpotAdapter;

public class SpotListActivity extends AppCompatActivity {

    public static String EXTRA_SELECTED_POSITION = "EXTRA_SELECTED_POSITION";
    private boolean isSelected = false;

    /** EXTRA_SELECTED_POSITION
     *
     * -2: 선택되지 않음
     * -1: "서울 어디나 좋아요!" 선택됨
     * 0 ~ n: 리스트 중 한개 선택됨
     */
    private int selectedPosition = -2;

    private ActivitySpotListBinding binding;

    private SpotAdapter spotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_spot_list);
    }

    private void setupViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvSpotList.setLayoutManager(layoutManager);
        spotAdapter = new SpotAdapter((v, position) -> {
            if (!isSelected) {
                v.setSelected(true);
                isSelected = true;
                selectedPosition = position;
            } else if (selectedPosition == position){
                v.setSelected(false);
                isSelected = false;
                selectedPosition = -2;
            }
        });
        binding.rcvSpotList.setAdapter(spotAdapter);
    }

    /*
     * 클릭 이벤트
     */
    public void onCancelClick(View v) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void onSelectCompleteClick(View v) {
        if (selectedPosition > -2) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SELECTED_POSITION, selectedPosition);
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            BasicUtils.showToast(this, getString(R.string.spot_list_msg));
        }
    }

    public void onAllClick(View v) {
        if (!isSelected) {
            v.setSelected(true);
            isSelected = true;
            selectedPosition = -1;
        } else if (selectedPosition == -1) {
            v.setSelected(false);
            isSelected = false;
            selectedPosition = -2;
        }
    }
}
