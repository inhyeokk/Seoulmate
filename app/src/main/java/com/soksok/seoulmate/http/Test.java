package com.soksok.seoulmate.http;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.Tour;
import com.soksok.seoulmate.http.model.User;
import com.soksok.seoulmate.http.model.request.LoginRequest;
import com.soksok.seoulmate.http.model.request.RegisterRequest;
import com.soksok.seoulmate.http.model.request.TourRequest;
import com.soksok.seoulmate.http.service.ApiService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/*
*  @ Package : com.soksok.seoulmate.http
*  @ File : Test.java
*  @ Auther : 김영서
*  @ Description : 서버와 연동되는 API 를 테스트하기위해 생성 및 개발
*  @ Example : Test t = new Test();
*  @ Example : t.testApiSet();
* */

public class Test  {

    ApiService apiService = ApiService.retrofit.create(ApiService.class);

    Call<BaseResponse<String>> registerCall = apiService.register(new RegisterRequest(
            "test@google.com",
            "1234",
            "testUser",
            20,
            "M",
            "dummyImage"));

    Call<BaseResponse<String>> loginCall = apiService.login(new LoginRequest(
            "test@google.com",
            "1234"
    ));

    Call<BaseResponse<List<User>>> getAllUsersCall = apiService.getAllUsers();

    Call<BaseResponse<User>> getUserCall = apiService.getUser("test@google.co");

    Call<BaseResponse<String>> setMateCall = apiService.setMate("test@google.com"); //   <---- 전달한 이메일로 메이트 변경
//  Call<BaseResponse<String>> setMateCall = apiService.setMate(null);                   <--- JWT로 메이트 변경

    Call<BaseResponse<List<Tour>>> getAllToursCall = apiService.getAllTours();

    Call<BaseResponse<ArrayList<Tour>>> getMyToursCall = apiService.getMyTour();

    Call<BaseResponse<List<Tour>>> getToursByEmailCall = apiService.getTourByEmail("test@google.com");

    Call<BaseResponse<String>> addTourCall = apiService.addTour(new TourRequest(
            "경복궁 여행하기",
            null,
            null,
            2,0,0,
            "경복궁",
            "GG","BB",
            "kys6879@naver.com"));

    public void testApiSet(){
        new TestHttpCall().execute(
                registerCall,loginCall,
                getAllUsersCall,getUserCall,setMateCall,
                getAllToursCall,getMyToursCall,getToursByEmailCall,
                addTourCall);
    }

    public class TestHttpCall extends  AsyncTask<Call,Void,String> {

        void register(Call<BaseResponse<String>> registerCall) throws IOException {

            Response<BaseResponse<String>> registerRes = registerCall.execute();
            int code = registerRes.code();
            if(code == 200){
                System.out.println("[Register] Success");
            } else if(code == 409){
                System.out.println("[Register] Fail");
                System.out.println("[Register] Duplicate User Info");
            }
        }

        String login(Call<BaseResponse<String>> loginCall) throws IOException {

            Response<BaseResponse<String>> loginRes = loginCall.execute();
            int code = loginRes.code();
            if(code == 200){
                System.out.println("[Login] Success");
                String token = loginRes.body().getMessage();
                System.out.println("[Login] Token : " + token);
                return token;
            } else if(code == 409){
                System.out.println("[Login] Fail");
                System.out.println("[Login] Incorrect email or password");
            }
            return null;
        }

        List<User> getAllUsers(Call<BaseResponse<List<User>>> getAllUsersCall) throws IOException {

            Response<BaseResponse<List<User>>> allUsersRes = getAllUsersCall.execute();
            int code = allUsersRes.code();
            if(code == 200){
                System.out.println("[getAllUsers] Success");
                List<User> Users = allUsersRes.body().getMessage();
                System.out.println("[getAllUsers] Users : " + Users);
                return Users;
            } else if(code == 409){
                System.out.println("[getAllUsers] Fail");
            }
            return null;
        }

        User getUser(Call<BaseResponse<User>> getUserCall) throws IOException {

            Response<BaseResponse<User>> userRes = getUserCall.execute();
            int code = userRes.code();
            if(code == 200){
                System.out.println("[getUser] Success");
                User User = userRes.body().getMessage();
                System.out.println("[getUser] User : " + User);
                return User;
            } else if(code == 409){
                Gson gson = new GsonBuilder().create();
                String msg = (String) gson.fromJson(userRes.errorBody().string(),BaseResponse.class).getMessage();
                System.out.println("[getUser] Fail");
                System.out.println("[getUser] Fail : " + msg);
            }
            return null;
        }

        void setMate(Call<BaseResponse<String>> setMateCall) throws IOException {

            Response<BaseResponse<String>> setMateRes = setMateCall.execute();
            int code = setMateRes.code();
            if(code == 200){
                System.out.println("[setMate] Success");
                String msg = setMateRes.body().getMessage();
                System.out.println("[setMate] User : " + msg);
            } else if(code == 406 || code == 409){
                Gson gson = new GsonBuilder().create();
                String msg = (String) gson.fromJson(setMateRes.errorBody().string(),BaseResponse.class).getMessage();
                System.out.println("[setMate] Fail");
                System.out.println("[setMate] Fail : " + msg);
            }
        }

        List<Tour> getAllTours(Call<BaseResponse<List<Tour>>> getAllToursCall) throws IOException {

            Response<BaseResponse<List<Tour>>> allToursRes = getAllToursCall.execute();
            int code = allToursRes.code();
            if(code == 200){
                System.out.println("[getAllTours] Success");
                List<Tour> tours = allToursRes.body().getMessage();
                System.out.println("[getAllTours] Tours : " + tours);
                return tours;
            } else {
                System.out.println("[getAllTours] Fail");
                return null;
            }
        }

        List<Tour> getMyTours(Call<BaseResponse<List<Tour>>> myToursCall) throws IOException {

            Response<BaseResponse<List<Tour>>> myToursRes = myToursCall.execute();
            int code = myToursRes.code();
            if(code == 200){
                System.out.println("[getMyTours] Success");
                List<Tour> tours = myToursRes.body().getMessage();
                System.out.println("[getMyTours] Tours : " + tours);
                return tours;
            } else {
                System.out.println("[getMyTours] Fail");
                return null;
            }
        }

        List<Tour> getToursByEmail(Call<BaseResponse<List<Tour>>> toursByEmailCall) throws IOException {

            Response<BaseResponse<List<Tour>>> toursRes = toursByEmailCall.execute();
            int code = toursRes.code();
            if(code == 200){
                System.out.println("[getMyTours] Success");
                List<Tour> tours = toursRes.body().getMessage();
                System.out.println("[getMyTours] Tours : " + tours);
                return tours;
            } else {
                System.out.println("[getMyTours] Fail");
                return null;
            }
        }

        void addTour(Call<BaseResponse<String>> addTourCall) throws IOException {
            Response<BaseResponse<String>> addTourRes = addTourCall.execute();
            int code = addTourRes.code();
            if(code == 200){
                System.out.println("[addTour] Success");
            } else if(code == 409){
                Gson gson = new GsonBuilder().create();
                String msg = (String) gson.fromJson(addTourRes.errorBody().string(),BaseResponse.class).getMessage();
                System.out.println("[addTour] Fail");
                System.out.println("[addTour] Fail : " + msg);
            }
        }

        @Override
        protected String doInBackground(Call... params) {
            Call<BaseResponse<String>> registerCall = params[0];
            Call<BaseResponse<String>> loginCall = params[1];
            Call<BaseResponse<List<User>>> getAllUsersCall = params[2];
            Call<BaseResponse<User>> getUserCall = params[3];
            Call<BaseResponse<String>> setMateCall = params[4];
            Call<BaseResponse<List<Tour>>> getAllToursCall= params[5];
            Call<BaseResponse<List<Tour>>> getMyToursCall= params[6];
            Call<BaseResponse<List<Tour>>> getToursByEmailCall= params[7];
            Call<BaseResponse<String>> addTourCall= params[8];

            try {
                // 회원가입
                this.register(registerCall);

                // 로그인
                String token = this.login(loginCall);
                if(token == null){
                    return null ;
                }

                // 모든 유저 불러오기
                this.getAllUsers(getAllUsersCall);

                // 특정 유저 불러오기
                this.getUser(getUserCall);

                // 메이트 신청하기
                this.setMate(setMateCall);

                // 모든 여행 불러오기
                this.getAllTours(getAllToursCall);

                // 나의 여행 불러오기
                this.getMyTours(getMyToursCall);

                // 특정 여행 불러오기
                this.getToursByEmail(getToursByEmailCall);

                // 여행 추가하기
//                this.addTour(addTourCall);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
