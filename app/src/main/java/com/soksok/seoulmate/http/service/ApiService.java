package com.soksok.seoulmate.http.service;

import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.Recommend;
import com.soksok.seoulmate.http.model.TitleRecommend;
import com.soksok.seoulmate.http.model.Tour;
import com.soksok.seoulmate.http.model.Tourist;
import com.soksok.seoulmate.http.model.User;
import com.soksok.seoulmate.http.model.request.LoginRequest;
import com.soksok.seoulmate.http.model.request.RegisterRequest;
import com.soksok.seoulmate.http.model.request.TourRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // # 유저 회원가입
    //   - 유저를 추가한다.
    //   - 리퀘스트할때만 쓰는 Model을 따로 작성. Class RegisterRequest.
    //   - 200 - 정상회원가입 , 409 - 이메일 중복 , 400 - 파라미터 미제공 , 500 - 서버에러
    @POST("auth/register")
    Call<BaseResponse<String>> register(@Body RegisterRequest body);

    // # 유저 로그인
    //   - 유저 로그인을 한다.
    //   - 리퀘스트할때만 쓰는 Model을 따로 작성. Class LoginRequest.
    //   -  JWT 토큰을 응답한다.
    //   -  200 - 정상로그인 , 409 - 아이디 비밀번호가 틀림
    @POST("auth/login")
    Call<BaseResponse<String>> login(@Body LoginRequest body);

    // # 모든 유저 불러오기
    //   - 저장된 모든 유저의 정보를 불러옴.
    //   - 200 - 정상 불러오기
    @GET("user/token")
    Call<BaseResponse<User>> getMyProfile();

    // # 내정보 불러오기
    //   -  나의 정보를 불러옴.
    //   - 200 - 정상 불러오기 , 409 - 없는유저
    @GET("user")
    Call<BaseResponse<List<User>>> getAllUsers();

    // # 특정유저 불러오기
    //   - 특정 유저의 정보를 불러옴.
    //   - 유저의 이메일(아이디) 를 path 파라미터로 전송함.
    //   - 200 정상 불러오기 , 409 - 일치하는 정보가 없음 (없는 이메일)
    @GET("user/{email}")
    Call<BaseResponse<User>> getUser(@Path("email") String email);

    // # 메이트신청
    //   - 일반 유저를 메이트 유저로 변경한다. (즉시 변경됨)
    //   - Path 파라미터로 메이트로 변경할 이메일을 받는다.
    //   - email 이 null 일경우 헤더에있는 JWT 정보로 메이트를 변경한다.
    //   - 성공시(200) 메이트로 변경된 이메일을 응답한다.
    //   - 200 - 정상변경 ,  406 - 이미 메이트인데 변경 할 경우 , 409 - 일치하는 정보가 없음 (없는 이메일) ,
    @PUT("user/mate/{email}")
    Call<BaseResponse<String>> setMate(@Path("email") String email);

    // # 모든 여행 불러오기
    //   - 저장된 모든 유저의 여행을 불러옴.
    //   - 성공시 200
    @GET("tour")
    Call<BaseResponse<List<Tour>>> getAllTours();

    // # 나의 여행 불러오기
    //  - 요청파라미터는 필요없음. JWT 토큰정보로 나의 여행을 불러옴
    //  - 등록된 여행이 없는 유저일 경우 빈 배열 응답
    //  - 200 - 성공 , 409 - 일치하는 정보가 없음 (없는 이메일)
    @GET("tour/token")
    Call<BaseResponse<ArrayList<Tour>>> getMyTour();

    // # 특정 여행 불러오기
    //  - 이메일을 파라미터로 전달함.
    //  - 등록된 여행이 없는 유저일 경우 빈 배열 응답
    //  - 200 - 성공 , 409 - 일치하는 정보가 없음 (없는 이메일)
    @GET("tour/{email}")
    Call<BaseResponse<List<Tour>>> getTourByEmail(@Path("email") String email);

    // # 여행 추가하기
    //  - 리퀘스트할때만 쓰는 Model을 따로 작성. Class TourRequest.
    //  - 200 - 성공 , 409 - 여행등록시 메이트가 일반유저일경우 (메이트가 아닌 유저를 메이트로 등록 할 경우), 400 - 파라미터 미제공
    @POST("tour")
    Call<BaseResponse<String>> addTour(@Body TourRequest body);

    // # 여행 제목 수정하기
    //   -  Tour.title , Tour.idx 를 파라미터로 필요로 함.
    //   - 200 성공
    @PUT("tour")
    Call<BaseResponse<String>> updateTitleTour(@Body TourRequest tour);

    // # 여행 삭제하기
    //   - 각 여행의 고유 idx 를 파라미터로 필요로 함.
    //   - 여행 불러오기 API는 idx를 포함한 정보를 응답함.
//    @DELETE("tour")
    @HTTP(method = "DELETE",path = "/tour", hasBody = true)
    Call<BaseResponse<String>> deleteTour(@Body TourRequest tour);

    // # 관광지 불러오기
    //  - 모든 서울특별시의 관광지 목록을 불러옴.
    //  - 관광지 목록은 여행을 추가할 때 선택 할 수 있음
    @GET("tourist")
    Call<BaseResponse<Tourist>> getAllTourists();

    // # 맛집 불러오기
    //   - 맛집 추천목록을 불러옴
    //   - 성공 200
    @GET("tourist/eat")
    Call<BaseResponse<Recommend>> getAlleats();

    // # 정보 불러오기
    //   - 정보 추천목록을 불러옴
    //   - 성공 200
    @GET("tourist/info")
    Call<BaseResponse<Recommend>> getAllinfos();

    // # 모든 추천리스트 불러오기
    //   - 모든 추천목록을 불러옴
    //   - 성공 200
    @GET("tourist/all")
    Call<BaseResponse<TitleRecommend>> getAllRecommands();

    // # 명소 불러오기
    //   - 명소 추천목록을 불러옴
    //   - 성공 200
    @GET("tourist/attr")
    Call<BaseResponse<Recommend>> getAllattrs();

    // 자동으로 API 요청시 토큰을 담을 수 있도로 Intercepter 설정
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request req = chain.request().newBuilder()
                            .addHeader("Authorization","bearer "+ PrefUtils.getToken())
                            .build();
                    return chain.proceed(req);
                }
            }).build();

    // static 으로 선언하여 정적으로 사용
    String localhost = "http://10.0.2.2:3000";
    String aws = "http://13.125.241.39:3000";

    public static final Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(localhost)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}


