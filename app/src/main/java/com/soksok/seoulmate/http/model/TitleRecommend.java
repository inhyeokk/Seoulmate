package com.soksok.seoulmate.http.model;

import java.util.ArrayList;
import java.util.List;

public class TitleRecommend {
    ArrayList<Recommend> eat;
    ArrayList<Recommend> info;
    ArrayList<Recommend> attr;

    public ArrayList<Recommend> getEat() {
        return eat;
    }

    public void setEat(ArrayList<Recommend> eat) {
        this.eat = eat;
    }

    public ArrayList<Recommend> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<Recommend> info) {
        this.info = info;
    }

    public ArrayList<Recommend> getAttr() {
        return attr;
    }

    public void setAttr(ArrayList<Recommend> attr) {
        this.attr = attr;
    }

    @Override
    public String toString() {
        return "TitleRecommend{" +
                "eat=" + eat +
                ", info=" + info +
                ", attr=" + attr +
                '}';
    }
}
