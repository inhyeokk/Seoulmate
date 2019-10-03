package com.soksok.seoulmate.view.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.databinding.ActivitySettingBinding;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.User;
import com.soksok.seoulmate.http.model.request.TourRequest;
import com.soksok.seoulmate.http.service.ApiService;
import com.soksok.seoulmate.view.like.LikeActivity;
import com.soksok.seoulmate.view.login.LoginActivity;
import com.soksok.seoulmate.view.main.MainActivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    public static final String EXTRA_LIKE = "EXTRA_LIKE";

    public static final String VALUE_LIKE_MATE = "VALUE_LIKE_MATE";
    public static final String VALUE_LIKE_SPOT = "VALUE_LIKE_SPOT";
    public static final String VALUE_LIKE_RESTAURANT = "VALUE_LIKE_RESTAURANT";
    public static final String VALUE_LIKE_INFORMATION = "VALUE_LIKE_INFORMATION";

    private ActivitySettingBinding binding;

    private BottomSheetBehavior behavior;

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

        setUserInfo();

        behavior = BottomSheetBehavior.from(binding.clBottomSheet);
        setBottomClickable(false);
    }

    /*
     * 클릭 이벤트
     */
    public void onUserClick(View v) {
        showChangeUserNameDialog();
    }

    public void onLayoutClick(View v) {
        setBottomClickable(false);
    }

    public void onCertifyClick(View v) {
        setBottomClickable(true);
    }

    public void onLikeMateClick(View v) {
        goToLikeActivity(VALUE_LIKE_MATE);
    }

    public void onLikeSpotClick(View v) {
        goToLikeActivity(VALUE_LIKE_SPOT);
    }

    public void onLikeRestaurantClick(View v) {
        goToLikeActivity(VALUE_LIKE_RESTAURANT);
    }

    public void onLikeInformationClick(View v) {
        goToLikeActivity(VALUE_LIKE_INFORMATION);
    }

    public void onLogoutClick(View v) {
        showLogoutDialog();
    }

    public void onBottomCancelClick(View v) {
        setBottomClickable(false);
    }

    public void onBottomContactClick(View v) {
        goToEmailApp();
        setBottomClickable(false);
    }

    private void setBottomClickable(boolean isClickable) {

        if (isClickable) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            binding.tvBottomCancel.setClickable(true);
            binding.tvBottomCancel.setFocusable(true);
            binding.tvBottomContact.setClickable(true);
            binding.tvBottomContact.setFocusable(true);
            binding.clBackground.setVisibility(View.VISIBLE);
            binding.clBackground.setClickable(true);
            binding.clBackground.setFocusable(true);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            binding.tvBottomCancel.setClickable(false);
            binding.tvBottomCancel.setFocusable(false);
            binding.tvBottomContact.setClickable(false);
            binding.tvBottomContact.setFocusable(false);
            binding.clBackground.setVisibility(View.GONE);
            binding.clBackground.setClickable(false);
            binding.clBackground.setFocusable(false);
        }
    }

    private void goToLikeActivity(String value) {

        Intent intent = new Intent(this, LikeActivity.class);
        intent.putExtra(EXTRA_LIKE, value);
        startActivity(intent);
    }

    private void goToLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goToEmailApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        startActivity(intent);
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showChangeUserNameDialog() {

        ChangeUserNameDialog dialog = new ChangeUserNameDialog(this);
        dialog.show();

        dialog.userName.observe(this, userName -> {
            Log.d("SETTING_NICKNAME", userName);
            // 변경된 닉네임
            ApiService apiService = ApiService.retrofit.create(ApiService.class);

            Call<BaseResponse<String>> updateTitleTour = apiService.updateUserNickname(userName);

            updateTitleTour.enqueue(new Callback<BaseResponse<String>>() {
                @Override
                public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                    if(response.code() == 200){
                        System.out.println("성공~!");
                        BasicUtils.showToast(getApplicationContext(),"변경성공!");

                        Call<BaseResponse<User>> myCall = apiService.getMyProfile();

                        myCall.enqueue(new Callback<BaseResponse<User>>() {
                            @Override
                            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                                if(response.code() == 200){
                                    System.out.println("성공~!");
                                    System.out.println("현재유저정보 : " + response.body().getMessage());
                                    User user = response.body().getMessage();

                                    binding.tvUserName.setText(user.getNickname());
                                    binding.tvUserEmail.setText(user.getEmail());

                                    goMainActivity();
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
                    } else {
                        // 그밖에 실패시.
                        BasicUtils.showToast(getApplicationContext(),"변경실패");
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

        }) ;
    }

    private void showLogoutDialog() {

        LogoutDialog dialog = new LogoutDialog(this);
        dialog.show();

        dialog.isLogout.observe(this, isLogout -> {
            if (isLogout) {
                /**
                 * 토큰 폐기 후 로그아웃
                 */
                PrefUtils.setToken("");
                goToLoginActivity();
            } else {
                // do nothing
            }
        });
    }

    private void setUserInfo(){

        ApiService apiService = ApiService.retrofit.create(ApiService.class);

        Call<BaseResponse<User>> myCall = apiService.getMyProfile();

        myCall.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if(response.code() == 200){
                    System.out.println("성공~!");
                    System.out.println("현재유저정보 : " + response.body().getMessage());
                    User user = response.body().getMessage();

                    binding.tvUserName.setText(user.getNickname());
                    binding.tvUserEmail.setText(user.getEmail());
                    if (user.getProfileImage() != null && !user.getProfileImage().equals("")) {
                        if(user.getIskakao() != 1){
                            Picasso.get().load(Uri.parse(user.getProfileImage())).into(binding.civProfile);
                        }else { // 일반로그인 이면
                            BindUtils.setImageBase64(binding.civProfile,user.getProfileImage());
                        }
                    }


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



//        User user = (User) getIntent().getSerializableExtra(MainActivity.EXTRA_USER_PROFILE);


//        System.out.println("#setUserInfo  user" + user);

    }
}
