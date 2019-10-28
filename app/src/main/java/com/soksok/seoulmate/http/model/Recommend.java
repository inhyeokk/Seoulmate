package com.soksok.seoulmate.http.model;

import com.google.gson.annotations.SerializedName;

public class Recommend {
    String classname;

    @SerializedName("idx")
    int num;
    String name;
    String addr;
    String tag;
    String image;
    String url;
    private TouristMap touristMap;

    public Recommend(String classname, int num, String name, String addr, String tag, String image , String url) {
        this.classname = classname;
        this.num = num;
        this.name = name;
        this.addr = addr;
        this.tag = tag;
        this.image = image;
        this.url = url;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TouristMap getTouristMap() {
        return touristMap;
    }

    public void setTouristMap(TouristMap touristMap) {
        this.touristMap = touristMap;
    }

    @Override
    public String toString() {
        return "Recommend{" +
                "classname='" + classname + '\'' +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", tag='" + tag + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", touristMap=" + touristMap +
                '}';
    }
}
