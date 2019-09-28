package com.soksok.seoulmate.http.model;

import java.io.Serializable;

public class User implements Serializable {
    String email; // 유저의 이메일. 아이디로 사용되며 중복 불가.
    String nickname; // 유저의 닉네임
    String password; // 유저의 패스워드
    int age; // 유저의 나이
    String gender; // 유저의 성별 M or W   M 은 남자 W 는 여자임.
    String type; // 유저의 타입. USR 은 일반유저 , MAT 는 메이트
    String cred_dt; // 생성일
    String profile_image;

    public String getProfileImage() {
        return profile_image;
    }

    public void setProfileImage(String profileImage) {
        this.profile_image = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCred_dt() {
        return cred_dt;
    }

    public void setCred_dt(String cred_dt) {
        this.cred_dt = cred_dt;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                ", cred_dt='" + cred_dt + '\'' +
                '}';
    }
}
