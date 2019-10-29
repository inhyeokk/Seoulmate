package com.soksok.seoulmate.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.services.naver.NaverAPI;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.databinding.ActivityLoginBinding;
import com.soksok.seoulmate.view.login.domain.LoginViewModel;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.request.LoginRequest;
import com.soksok.seoulmate.http.service.ApiService;
import com.soksok.seoulmate.view.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final int FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0L;

    private SessionCallback callback;
    private OAuthLogin oAuthLoginModule;

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel = new LoginViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();
//        initKakaoLoginModule();
        initNaverLoginModule();
        subscribe();
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    private void setupViews() {

    }

    private void subscribe() {

        /**
         * 네이버 로그인 후 이메일 수신
         */
        loginViewModel.naverEmail.observe(LoginActivity.this, email -> {
            /**
             * 요청 성공
             */
            System.out.println("네이버로그인 후 : " + email );
            goToMainActivity();
        });
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
    public void onLoginClick(View v) {
        /** 리퀘스트
         *  로그인 성공 여부에 따른 분기
         */
        String emailId = binding.edEmailId.getText().toString();
        String password = binding.edPassword.getText().toString();

        System.out.println("#LoginActivity !!!!");
        System.out.println("#emailId " +emailId);
        System.out.println("#password " +password);

        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        Call<BaseResponse<String>> loginCall = apiService.login(new LoginRequest(
                emailId,
                password
        ));

        loginCall.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if(response.code() == 200){
                    System.out.println("성공~!");
                    String token = response.body().getMessage();
                    PrefUtils.setToken(token);
                    goToMainActivity();
                    // 서버와 통신하여 로그인 성공시
                } else {
                    // 그밖에 실패시.
                    BasicUtils.showToast(getApplicationContext(),"로그인 실패");
                    System.out.println(response.code());
                    System.out.println(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                System.out.println("실패!!");
            }
        });
    }

    public void onJoinClick(View v) {
        goToJoinActivity();
    }

    public void onKakaoLoginClick(View v) {
        initKakaoLoginModule();
        callback.isLogin.observe(this, isLogin -> {
            if (isLogin) {
                // 일단 더미 JWT 토큰 넣어둠
//                PrefUtils.setToken("eyJhbGciOiJIUzI1NiJ9.a3lzNjg3OUBuYXZlci5jb20.Jqb7ZtryZapuIbjYB4_bL8hPKB-jRRave1H9QYJYgMM");
                // 로그인 성공
                goToMainActivity();
            } else {
                BasicUtils.showToast(this,"로그인실패!");
                // 실패
                // do nothing
            }
        });
    }

    public void onNaverLoginClick(View v) {
        oAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler);
    }

    private void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goToJoinActivity() {

        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    /*
     * 카카오 로그인
     */
    private void initKakaoLoginModule() {

        callback = new SessionCallback();
        Session session = Session.getCurrentSession();
        session.addCallback(callback);
        session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    /*
     * 네이버 로그인
     */
    private void initNaverLoginModule() {
        oAuthLoginModule = OAuthLogin.getInstance();
        /** Seoulmate Application 에서 초기화
         *  로그인 or 로그아웃 구현시 싱글톤 객체 쓰면됨
         */
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = oAuthLoginModule.getAccessToken(getApplicationContext());
                String refreshToken = oAuthLoginModule.getRefreshToken(getApplicationContext());
                long expiresAt = oAuthLoginModule.getExpiresAt(getApplicationContext());
                String tokenType = oAuthLoginModule.getTokenType(getApplicationContext());

                /** 네이버 로그인 토큰
                 *  갱신 토큰이 있으면 접근 토큰의 갱신을 시도합니다.
                 *  − 갱신에 성공하면 OAuthLoginHandler 객체가 호출됩니다.
                 *  − 갱신에 실패하면 로그인 창이 나타납니다.
                 *  갱신 토큰이 없으면 로그인 창이 나타납니다.
                 *  * 접근 토큰 갱신 관련 주의: 접근토큰은 일정 시간(현재 1시간)이 지나면 만료되기 때문에
                 *    만료시간이 지난 경우 refreshAccessToken() 을 호출해서 access token 을 갱신해줘야 합니다.
                 *    또한 refreshAccessToken() 을 사용하시는 경우 메쏘드의 실행이 끝나면 access token 을
                 *    받을 수 있기 때문에 좀 더 편리하게 개발하실 수 있습니다.
                 */
                loginViewModel.getNaverEmail(accessToken);
            } else {
                String errorCode = oAuthLoginModule.getLastErrorCode(getApplicationContext()).getCode();
                String errorDesc = oAuthLoginModule.getLastErrorDesc(getApplicationContext());
                BasicUtils.showToast(getApplicationContext(),
                        "errorCode:" + errorCode + ", errorDesc:" + errorDesc);
            }
        }
    };
}
