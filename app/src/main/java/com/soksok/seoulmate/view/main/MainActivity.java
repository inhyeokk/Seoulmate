package com.soksok.seoulmate.view.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.databinding.ActivityMainBinding;
import com.soksok.seoulmate.databinding.ItemMyTripMenuBinding;
import com.soksok.seoulmate.http.Test;
import com.soksok.seoulmate.view.main.adapter.MyTripAdapter;
import com.soksok.seoulmate.view.main.adapter.MyTripItemListener;
import com.soksok.seoulmate.view.main.data.MainRepositoryImpl;
import com.soksok.seoulmate.view.main.domain.MainViewModel;
import com.soksok.seoulmate.view.match.MatchActivity;
import com.soksok.seoulmate.view.setting.SettingActivity;

public class MainActivity extends AppCompatActivity {

    private static final int FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0L;

    public static int REQUEST_CHANGE_ALBUM_TITLE = 2001;
    public static String EXTRA_ALBUM_TITLE = "EXTRA_ALBUM_TITLE";

    private MainViewModel viewModel = new MainViewModel(new MainRepositoryImpl());
    private ActivityMainBinding binding;

    private MyTripAdapter upcomingTripAdapter;
    private MyTripAdapter lastTripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();

//       new Test().testApiSet();

    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    private void setupViews() {

        if (isMyTripList()) {
            binding.layoutMain.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));
            binding.nsvMyTrip.setVisibility(View.VISIBLE);

            // TODO 현재날짜에 따른 분기
            // 다가오는 여행
            LinearLayoutManager upcomingLayoutManager = new LinearLayoutManager(this);
            binding.rcvUpcomingTrip.setLayoutManager(upcomingLayoutManager);
            upcomingTripAdapter = new MyTripAdapter(upComingListener);
            binding.rcvUpcomingTrip.setAdapter(upcomingTripAdapter);

            // 지난 여행
            LinearLayoutManager lastLayoutManager = new LinearLayoutManager(this);
            binding.rcvLastTrip.setLayoutManager(lastLayoutManager);
            lastTripAdapter = new MyTripAdapter(lastListener);
            binding.rcvLastTrip.setAdapter(lastTripAdapter);
        } else {
            binding.layoutMain.setBackground(getResources().getDrawable(R.drawable.ic_main_bg, null));
            binding.nsvMyTrip.setVisibility(View.INVISIBLE);
        }
    }

    private void subscribeUI() {

    }

    public boolean isMyTripList() {
        return true;
    }

    /**
     * 뒤로가기 버튼 연속 두번 클릭 시 앱 종료
     */
    @Override
    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (!(0 > intervalTime || FINISH_INTERVAL_TIME < intervalTime)) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            BasicUtils.showToast(this, getString(R.string.common_toast_back_press));
        }
    }

    /*
     * 클릭 이벤트
     */
    public void onProfileClick(View v) {
        goToSettingActivity();
    }

    public void onMyTripClick(View v) {

    }

    public void onSearchClick(View v) {
        goToMatchActivity();
    }

    public void onListClick(View v) {

    }

    private void goToSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void goToMatchActivity() {
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
    }

    /*
     * 내 여행 리스트의 메뉴버튼을
     * 눌렀을 때 나타나는 팝업 메뉴
     */
    private MyTripItemListener upComingListener = (v, position) -> {
        showPopupMenu(v);
    };

    private MyTripItemListener lastListener = (v, position) -> {
        showPopupMenu(v);
    };

    private void showPopupMenu(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemMyTripMenuBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_my_trip_menu, null, false);

        PopupWindow popupWindow = new PopupWindow(binding.getRoot(), BasicUtils.dpToPx(150), BasicUtils.dpToPx(140), true);
        Rect location = BasicUtils.locateView(v);
        popupWindow.showAtLocation(v, Gravity.TOP|Gravity.START, location.left, location.top);

        binding.tvChangeAlbumTitle.setOnClickListener(v1 -> {
            // 앨범 제목 변경
            showChangeAlbumTitleDialog();
            popupWindow.dismiss();
        });
        binding.tvChangeAlbumCover.setOnClickListener(v1 -> {
            // 앨범 커버사진 변경
            popupWindow.dismiss();
        });
        binding.tvDeleteAlbum.setOnClickListener(v1 -> {
            // 앨범 삭제
            showDeleteAlbumDialog();
            popupWindow.dismiss();
        });
    }

    private void showChangeAlbumTitleDialog() {
        new ChangeAlbumTitleDialog(this).show();
    }

    private void showDeleteAlbumDialog() {
        new DeleteAlbumDialog(this).show();
    }


}
