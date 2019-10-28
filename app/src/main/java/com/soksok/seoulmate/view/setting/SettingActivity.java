package com.soksok.seoulmate.view.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.soksok.seoulmate.http.model.request.LoginRequest;
import com.soksok.seoulmate.http.model.request.TourRequest;
import com.soksok.seoulmate.http.service.ApiService;
import com.soksok.seoulmate.view.like.LikeActivity;
import com.soksok.seoulmate.view.login.LoginActivity;
import com.soksok.seoulmate.view.main.MainActivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HEAD;

public class SettingActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1001;
    private static final int REQUEST_GALLERY = 5001;

    public static final String EXTRA_LIKE = "EXTRA_LIKE";

    public static final String VALUE_LIKE_MATE = "VALUE_LIKE_MATE";
    public static final String VALUE_LIKE_SPOT = "VALUE_LIKE_SPOT";
    public static final String VALUE_LIKE_RESTAURANT = "VALUE_LIKE_RESTAURANT";
    public static final String VALUE_LIKE_INFORMATION = "VALUE_LIKE_INFORMATION";

    private boolean isProfileChanged = false;

    private ActivitySettingBinding binding;

    private BottomSheetBehavior behavior;

    private ApiService apiService = ApiService.retrofit.create(ApiService.class);
    private Call<BaseResponse<String>> updateUserProfileImage;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY:
                switch (resultCode) {
                    case RESULT_OK:
                        Uri uri = data.getData();
                        /* TODO
                         * 프로필 이미지 변경
                         */
                        String imageString = BasicUtils.fromURIToBase64(uri);

                        updateUserProfileImage = apiService.updateUserProfileImage(new User(imageString));

                        updateUserProfileImage.enqueue(new Callback<BaseResponse<String>>() {
                            @Override
                            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                                BindUtils.setCircleGalleryURI(binding.civProfile, uri);
                                isProfileChanged = true;
                                System.out.println("onResponse");
                                System.out.println("onResponse -->" + response.errorBody());
                            }
                            @Override
                            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                                BasicUtils.showToast(getApplicationContext(),t.getMessage());
                                System.out.println("onFailure");

                            }

                        });
                        break;
                    case RESULT_CANCELED:
                        // do nothing
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    goToGallery();
                } else {
                    // permission denied
                    // do nothing
                }
                break;
        }
    }

    /*
     * 클릭 이벤트
     */
    @Override
    public void onBackPressed() {
        if (isProfileChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    public void onProfileClick(View v) {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            goToGallery();
        } else {
            requestPermission();
        }
    }

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

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSION
        );
    }

    private void goToGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
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
                            if(user.getProfileImage().substring(0,1).equals("/")){
                                BindUtils.setImageBase64(binding.civProfile, user.getProfileImage());
                            } else {
                                Picasso.get().load(Uri.parse(user.getProfileImage())).into(binding.civProfile);
                            }
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
    }
}