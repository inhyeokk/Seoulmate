package com.soksok.seoulmate.http.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tour implements Serializable {
    String idx;
    String user_email;
    String name;
    String start_date;
    String end_date;
    int adult;
    int child;
    int infant;
    String mate;
    String image;
    String soon;
    String tourist_name;
    String tour_style;
    String tour_type;

    // constructor using MyTripAdapter
    public Tour(String idx, String name, String start_date, String end_date, int adult, int child, int infant, String mate, String image) {
        this.idx = idx;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.adult = adult;
        this.child = child;
        this.infant = infant;
        this.mate = mate;
        this.image = image;
    }

    // constructor general
    public Tour(String idx, String user_email, String name, String start_date, String end_date, int adult, int child, int infant, String mate, String image, String soon, String tourist_name, String tour_style, String tour_type) {
        this.idx = idx;
        this.user_email = user_email;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.adult = adult;
        this.child = child;
        this.infant = infant;
        this.mate = mate;
        this.image = image;
        this.soon = soon;
        this.tourist_name = tourist_name;
        this.tour_style = tour_style;
        this.tour_type = tour_type;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getInfant() {
        return infant;
    }

    public void setInfant(int infant) {
        this.infant = infant;
    }

    public String getMate() {
        return mate;
    }

    public void setMate(String mate) {
        this.mate = mate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSoon() {
        return soon;
    }

    public void setSoon(String soon) {
        this.soon = soon;
    }

    public String getTourist_name() {
        return tourist_name;
    }

    public void setTourist_name(String tourist_name) {
        this.tourist_name = tourist_name;
    }

    public String getTour_style() {
        return tour_style;
    }

    public void setTour_style(String tour_style) {
        this.tour_style = tour_style;
    }

    public String getTour_type() {
        return tour_type;
    }

    public void setTour_type(String tour_type) {
        this.tour_type = tour_type;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "idx='" + idx + '\'' +
                ", user_email='" + user_email + '\'' +
                ", name='" + name + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", adult=" + adult +
                ", child=" + child +
                ", infant=" + infant +
                ", mate='" + mate + '\'' +
                ", image='" + image + '\'' +
                ", soon='" + soon + '\'' +
                ", tourist_name='" + tourist_name + '\'' +
                ", tour_style='" + tour_style + '\'' +
                ", tour_type='" + tour_type + '\'' +
                '}';
    }
}
