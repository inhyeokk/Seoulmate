package com.soksok.seoulmate.view.chat.entity;

public class ChatItem {

    public enum Type {
        USER(0),
        PARTNER(1),
        TEMP(2),
        USER_IMAGE(3);

        private int value;

        Type (int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Type type;
    private String profile;
    private int profileDrawable;
    private String content;
    private String image;
    private String time;

    // user or user_image
    public ChatItem(Type type, String content, String time) {
        this.type = type;
        if (type == Type.USER) {
            this.content = content;
        } else if (type == Type.USER_IMAGE) {
            this.image = content;
        }
        this.time = time;
    }

    // partner
    public ChatItem(Type type, String profile, String content, String time) {
        this.type = type;
        this.profile = profile;
        this.content = content;
        this.time = time;
    }

    // temp
    public ChatItem(Type type, int profileDrawable, String content, String time) {
        this.type = type;
        this.profileDrawable = profileDrawable;
        this.content = content;
        this.time = time;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getProfileDrawable() {
        return profileDrawable;
    }

    public void setProfileDrawable(int profileDrawable) {
        this.profileDrawable = profileDrawable;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
