package com.soksok.seoulmate.http.model;

public class Recommend {
    String classname;
    int num;
    String name;
    String addr;
    String tag;
    String image;

    public Recommend(String classname, int num, String name, String addr, String tag, String image) {
        this.classname = classname;
        this.num = num;
        this.name = name;
        this.addr = addr;
        this.tag = tag;
        this.image = image;
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

    @Override
    public String toString() {
        return "Recommend{" +
                "classname='" + classname + '\'' +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", tag='" + tag + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
