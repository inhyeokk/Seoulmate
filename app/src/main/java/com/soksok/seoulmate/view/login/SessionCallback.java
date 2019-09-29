package com.soksok.seoulmate.view.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.User;
import com.soksok.seoulmate.http.model.request.LoginRequest;
import com.soksok.seoulmate.http.model.request.RegisterRequest;
import com.soksok.seoulmate.http.service.ApiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionCallback implements ISessionCallback {

    public MutableLiveData<Boolean> isLogin = new MutableLiveData<>();

    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
        requestMe();
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe() {
        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            // 세션 오픈 실패. 세션이 삭제된 경우,
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
                isLogin.postValue(false);
            }

            // 사용자정보 요청에 성공한 경우,
            @Override
            public void onSuccess(MeV2Response userProfile) {
                Log.e("SessionCallback :: ", "onSuccess");
                String nickname = userProfile.getNickname();
                String email = userProfile.getKakaoAccount().getEmail();
                String profileImagePath = userProfile.getProfileImagePath();
                String thumbnailPath = userProfile.getThumbnailImagePath();
                long id = userProfile.getId();

                /** 카카오 로그인 결과
                 *
                 */

                System.out.println("#onSuccess : " +nickname);
                System.out.println("#onSuccess : " +email);
                System.out.println("#onSuccess : " +profileImagePath);
                System.out.println("#onSuccess : " +thumbnailPath);
                System.out.println("#onSuccess : " +id);

                ApiService apiService = ApiService.retrofit.create(ApiService.class);
                Call<BaseResponse<User>> getUserCall = apiService.getUseradmin(email);
                Call<BaseResponse<String>> registerCall = apiService.register(new RegisterRequest(
                        email,
                        "1234",
                        nickname,
                        20,
                        "M",
                        thumbnailPath,
                        2
                ));

//                PrefUtils.setToken("eyJhbGciOiJIUzI1NiJ9.a3lzNjg3OUBuYXZlci5jb20.Jqb7ZtryZapuIbjYB4_bL8hPKB-jRRave1H9QYJYgMM");
                // 서버에 있는지 확인
                getUserCall.enqueue(new Callback<BaseResponse<User>>() {

                    @Override
                    public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
//                        PrefUtils.setToken("");
                        System.out.println("Success!!~!");
                        if(response.code() == 200){
                            // 서버에 유저가 이미 등록되어 있으니 로그인
                            System.out.println("성공~!~!");
                            System.out.println("성공~!~! :" +response.body().getMessage() );

                            Call<BaseResponse<String>> loginCall = apiService.login(new LoginRequest(
                                    email,
                                    "1234"
                            ));
                            login(loginCall);
                        } else if(response.code() == 409){
                            // 서버에 유저가 없으면 회원가입
                            registerCall.enqueue(new Callback<BaseResponse<String>>() {
                                @Override
                                public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                                    if(response.code() == 200){
                                        // 서버와 통신하여 회원가입 성공시
                                        System.out.println("성공~!~!");
                                        System.out.println("성공~!~! :" +response.body().getMessage() );

                                        Call<BaseResponse<String>> loginCall = apiService.login(new LoginRequest(
                                                email,
                                                "1234"
                                        ));
                                        login(loginCall);
                                    } else {
                                        // 그밖에 실패시.
                                        try {
                                            System.out.println("registerCall 실패!!");
                                            System.out.println(response.code());
                                            System.out.println(response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                                    try {
                                        System.out.println("#getUserCall 실패!!");
                                        System.out.println(response.code());
                                        System.out.println(response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                      }
                        else {
                            // 그밖에 실패시.
                            try {
                                System.out.println("실패!!");
                                System.out.println(response.code());
                                System.out.println(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
//                        PrefUtils.setToken("");
                        System.out.println("Get User Call 실패!!" + t.getMessage());
                    }
                });
            }

            // 사용자 정보 요청 실패
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
                isLogin.postValue(false);
            }
        });
    }

    private void login(Call<BaseResponse<String>> loginCall) {

        loginCall.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if(response.code() == 200){
                    String token = response.body().getMessage();
                    System.out.println("loginCall 성공~! token --->" + token);
                    PrefUtils.setToken(token);
                    // 로그인 성공시 결과 전송
                    isLogin.postValue(true);
                    // 서버와 통신하여 로그인 성공시
                } else {
                    // 그밖에 실패시.
//                                        BasicUtils.showToast(,"로그인 실패");
                    System.out.println("loginCall 실패! token " );

                    System.out.println(response.code());
                    System.out.println(response.errorBody().toString());

                    isLogin.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                System.out.println("#login : "+ t.getMessage());
                isLogin.postValue(false);
            }
        });
    }
}

