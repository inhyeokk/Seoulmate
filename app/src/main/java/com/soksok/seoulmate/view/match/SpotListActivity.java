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

import java.util.ArrayList;

/**
 * Mh2
 */
public class SpotListActivity extends AppCompatActivity {

    public static String EXTRA_SELECTED_POSITION = "EXTRA_SELECTED_POSITION";
    private boolean isSelected = false;

    /** EXTRA_SELECTED_POSITION
     *
     * -1: 선택되지 않음
     * 0 ~ n: 리스트 중 한개 선택됨
     */
    private int selectedPosition = -1;

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
        spotAdapter = new SpotAdapter(getSpots(), (v, position) -> {
            if (!isSelected) {
                v.setSelected(true);
                isSelected = true;
                selectedPosition = position;
            } else if (selectedPosition == position){
                v.setSelected(false);
                isSelected = false;
                selectedPosition = -1;
            }
        });
        binding.rcvSpotList.setAdapter(spotAdapter);
    }

    private ArrayList<Integer> getSpots() {

        ArrayList<Integer> spots = new ArrayList<>();

        spots.add(R.drawable.selector_list_spot0);
        spots.add(R.drawable.selector_list_spot1);
        spots.add(R.drawable.selector_list_spot2);
        spots.add(R.drawable.selector_list_spot3);
        spots.add(R.drawable.selector_list_spot4);
        spots.add(R.drawable.selector_list_spot5);
        spots.add(R.drawable.selector_list_spot6);
        spots.add(R.drawable.selector_list_spot7);
        spots.add(R.drawable.selector_list_spot8);
        spots.add(R.drawable.selector_list_spot9);
        spots.add(R.drawable.selector_list_spot10);

        return spots;
    }

    /*
     * 클릭 이벤트
     */
    public void onCancelClick(View v) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void onSelectCompleteClick(View v) {
        if (selectedPosition > -1) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SELECTED_POSITION, selectedPosition);
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            BasicUtils.showToast(this, getString(R.string.spot_list_msg));
        }
    }
}
