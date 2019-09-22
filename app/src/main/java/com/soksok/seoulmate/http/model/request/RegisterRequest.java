package com.soksok.seoulmate.http.model.request;

public class RegisterRequest {
    String email; // 유저의 이메일 (아이디) 중복불가.
    String password; // 유저의 패스워드
    String nickname; // 유저의 닉네임
    int age; // 유저의 나이
    String gender; // 유저의 성별 M or W 남자는 M 여자는 W

    public RegisterRequest(String email, String password, String nickname, int age, String gender) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
    }
}
