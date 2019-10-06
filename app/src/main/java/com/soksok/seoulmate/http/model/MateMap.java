package com.soksok.seoulmate.http.model;

public class MateMap {
    String mate_email;
    String user_email;
    int mlike;

    public String getMate_email() {
        return mate_email;
    }

    public void setMate_email(String mate_email) {
        this.mate_email = mate_email;
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
        return "MateMap{" +
                "mate_email='" + mate_email + '\'' +
                ", user_email='" + user_email + '\'' +
                ", mlike=" + mlike +
                '}';
    }
}
