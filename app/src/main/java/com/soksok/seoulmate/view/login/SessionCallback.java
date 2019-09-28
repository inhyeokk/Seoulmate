package com.soksok.seoulmate.view.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

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

                // 로그인 성공시 결과 전송
                isLogin.postValue(true);
            }

            // 사용자 정보 요청 실패
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
                isLogin.postValue(false);
            }
        });
    }
}

