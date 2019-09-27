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
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.databinding.ActivityMainBinding;
import com.soksok.seoulmate.databinding.ItemMyTripMenuBinding;
import com.soksok.seoulmate.http.Test;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.User;
import com.soksok.seoulmate.http.model.request.LoginRequest;
import com.soksok.seoulmate.http.model.request.TourRequest;
import com.soksok.seoulmate.http.service.ApiService;
import com.soksok.seoulmate.view.chat.ChatActivity;
import com.soksok.seoulmate.view.main.adapter.MyTripAdapter;
import com.soksok.seoulmate.view.main.adapter.MyTripItemListener;
import com.soksok.seoulmate.view.main.data.MainRepositoryImpl;
import com.soksok.seoulmate.view.main.domain.MainViewModel;
import com.soksok.seoulmate.view.match.MatchActivity;
import com.soksok.seoulmate.view.recommend.RecommendActivity;
import com.soksok.seoulmate.view.setting.SettingActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0L;

    public static int REQUEST_CHAT = 2001;
    public static String EXTRA_CHAT_TITLE = "EXTRA_CHAT_TITLE";

    private MainViewModel viewModel = new MainViewModel(new MainRepositoryImpl());
    private ActivityMainBinding binding;

    private MyTripAdapter upcomingTripAdapter;
    private MyTripAdapter lastTripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();
        getProfile();

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
            upcomingTripAdapter = new MyTripAdapter(new MyTripItemListener() {
                @Override
                public void onLayoutClick(View v, String title) {
                    goToChatActivity(title);
                }

                @Override
                public void onMenuClick(View v, int position) {
                    showPopupMenu(v);
                }
            });
            binding.rcvUpcomingTrip.setAdapter(upcomingTripAdapter);

            // 지난 여행
            LinearLayoutManager lastLayoutManager = new LinearLayoutManager(this);
            binding.rcvLastTrip.setLayoutManager(lastLayoutManager);
            lastTripAdapter = new MyTripAdapter(new MyTripItemListener() {
                @Override
                public void onLayoutClick(View v, String title) {
                    goToChatActivity(title);
                }

                @Override
                public void onMenuClick(View v, int position) {
                    showPopupMenu(v);
                }
            });
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
        goToRecommendActivity();
    }

    private void goToSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void goToChatActivity(String title) {

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(EXTRA_CHAT_TITLE, title);
        startActivity(intent);
    }

    private void goToMatchActivity() {
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
    }

    private void goToRecommendActivity() {
        Intent intent = new Intent(this, RecommendActivity.class);
        startActivity(intent);
    }

    /*
     * 내 여행 리스트의 메뉴버튼을
     * 눌렀을 때 나타나는 팝업 메뉴
     */
    private void showPopupMenu(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemMyTripMenuBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_my_trip_menu, null, false);

        PopupWindow popupWindow = new PopupWindow(binding.getRoot(), BasicUtils.dpToPx(150), BasicUtils.dpToPx(90), true);
        Rect location = BasicUtils.locateView(v);
        popupWindow.showAtLocation(v, Gravity.TOP|Gravity.START, location.left, location.top);

        binding.tvChangeAlbumTitle.setOnClickListener(v1 -> {
            // 앨범 제목 변경
            showChangeAlbumTitleDialog();
            popupWindow.dismiss();
        });
//        binding.tvChangeAlbumCover.setOnClickListener(v1 -> {
//            // 앨범 커버사진 변경
//            popupWindow.dismiss();
//        });
        binding.tvDeleteAlbum.setOnClickListener(v1 -> {
            // 앨범 삭제
            showDeleteAlbumDialog();
            popupWindow.dismiss();
        });
    }

    private void showChangeAlbumTitleDialog() {
        ChangeAlbumTitleDialog dialog = new ChangeAlbumTitleDialog(this);
        dialog.show();

        dialog.albumTitle.observe(this, title -> {
            /** 변경된 앨범 제목
             *  입력된 텍스트가 있고 확인버튼 눌렀을 때 값 전달
             */
            ApiService apiService = ApiService.retrofit.create(ApiService.class);

            TourRequest tour = new TourRequest();
            tour.setIdx("1234");
            tour.setName(title);

            Call<BaseResponse<String>> updateTitleTour = apiService.updateTitleTour(tour);

            updateTitleTour.enqueue(new Callback<BaseResponse<String>>() {
                @Override
                public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                    if(response.code() == 200){
                        System.out.println("성공~!");
                        // 서버와 통신하여 로그인 성공시
                    } else {
                        // 그밖에 실패시.
                        BasicUtils.showToast(getApplicationContext(),"삭제 실패");
                        System.out.println(response.code());
                        System.out.println(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                    System.out.println("실패!!");
                }
            });
        });
    }

    private void showDeleteAlbumDialog() {
        DeleteAlbumDialog dialog = new DeleteAlbumDialog(this);
        dialog.show();

        dialog.isDelete.observe(this, isDelete -> {
            if (isDelete) {
                /** 앨범 삭제
                 */

                ApiService apiService = ApiService.retrofit.create(ApiService.class);

                TourRequest tourRequest = new TourRequest("123456");

                Call<BaseResponse<String>> deleteTourCall = apiService.deleteTour(tourRequest);

                deleteTourCall.enqueue(new Callback<BaseResponse<String>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                        if(response.code() == 200){
                            System.out.println("성공~!");
                            // 서버와 통신하여 로그인 성공시
                        } else {
                            // 그밖에 실패시.
                            BasicUtils.showToast(getApplicationContext(),"삭제 실패");
                            System.out.println(response.code());
                            System.out.println(response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                        System.out.println("실패!!");
                    }
                });
            } else {
                // do nothing
            }
        });
    }


    // 메인엑티비티접근시 가지고있는 token 으로 현재 유저의 정보를 가져옴
    private void getProfile(){

        System.out.println("#MainActivity !!!! #getProfile");

        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        Call<BaseResponse<User>> myCall = apiService.getMyProfile();

        myCall.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if(response.code() == 200){
                    System.out.println("성공~!");
                    System.out.println("현재유저정보 : " + response.body().getMessage());
                    User user = response.body().getMessage();
                    String title = BindUtils.setMainTitle(user.getNickname());
                    binding.tvTitle.setText(title);
                } else {
                    BasicUtils.showToast(getApplicationContext(),"유저 정보 로딩 실패");
                    System.out.println(response.code());
                    System.out.println(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                System.out.println("실패!!");
            }
        });
    }
}
