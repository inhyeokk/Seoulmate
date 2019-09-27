package com.soksok.seoulmate.http.model.request;

import java.util.Date;

public class TourRequest {

    String idx;
    String name; // 여행 제목
    String startDate; // 여행 시작 날짜
    String endDate; // 여행 끝나는 날짜
    int adult; // 성인 몇명
    int child; // 어린이 몇명
    int infant; // 유아 몇명
    String touristName; // 관광지 이름
    String tourStyle; // MM 맛집 , HY 휴양 , GG 관광 , AT 액티비티 , MC, 문화체험
    String tourType; // GG 적극적인 , DG 도전적인 , YY 여유로운 , EJ 의지하고싶은, BB 바삐움직이는
    String mateEmail; // 메이트의 이메일 (가이드의 이메일)

    public TourRequest() {
    }

    public TourRequest(String idx) {
        this.idx = idx;
    }

    public TourRequest(String name, String startDate, String endDate, int adult, int child, int infant, String touristName, String tourStyle, String tourType, String mateEmail) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adult = adult;
        this.child = child;
        this.infant = infant;
        this.touristName = touristName;
        this.tourStyle = tourStyle;
        this.tourType = tourType;
        this.mateEmail = mateEmail;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
