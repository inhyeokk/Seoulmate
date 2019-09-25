package com.soksok.seoulmate;

import android.app.Application;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.nhn.android.naverlogin.OAuthLogin;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.view.login.KakaoSDKAdapter;

import java.lang.ref.WeakReference;

public class SeoulmateApplication extends Application {

    private static WeakReference<Context> context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(getApplicationContext());
        initUtils();
        KakaoSDK.init(new KakaoSDKAdapter());
        initNaverLoginModule();
    }

    private void initUtils() {

        BasicUtils.init(this);
        BindUtils.init(this);
    }

    private void initNaverLoginModule() {

        OAuthLogin oAuthLoginModule = OAuthLogin.getInstance();
        oAuthLoginModule.init(
                this,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.app_name)
        );
    }

    public static Context getSeoulmateApplicationContext() {
        return context.get();
    }
}
