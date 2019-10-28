package com.soksok.seoulmate.http.model;

public class TouristMap {
    int idx;
    String user_email;
    int mlike;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getMlike() {
        return mlike;
    }

    public void setMlike(int mlike) {
        this.mlike = mlike;
    }

    @Override
    public String toString() {
        return "TouristMap{" +
                "idx=" + idx +
                ", user_email='" + user_email + '\'' +
                ", mlike=" + mlike +
                '}';
    }
}