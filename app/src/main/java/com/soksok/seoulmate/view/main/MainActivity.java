package com.soksok.seoulmate.view.main;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ActivityMainBinding;
import com.soksok.seoulmate.databinding.ItemMyTripMenuBinding;
import com.soksok.seoulmate.view.main.adapter.MyTripAdapter;
import com.soksok.seoulmate.view.main.adapter.MyTripItemListener;
import com.soksok.seoulmate.view.main.data.MainRepositoryImpl;
import com.soksok.seoulmate.view.main.domain.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel = new MainViewModel(new MainRepositoryImpl());
    private ActivityMainBinding binding;

    private MyTripAdapter upcomingTripAdapter;
    private MyTripAdapter lastTripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();
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

    /*
     * 클릭 이벤트
     */
    public void onProfileClick(View v) {

    }

    public void onMyTripClick(View v) {

    }

    public void onSearchClick(View v) {

    }

    public void onListClick(View v) {

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
        PopupWindow popupWindow = new PopupWindow(binding.getRoot(), 600, 500, true);
        Rect location = locateView(v);
        popupWindow.showAtLocation(v, Gravity.TOP|Gravity.START, location.left, location.top);
    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0] - 600;
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }
}
