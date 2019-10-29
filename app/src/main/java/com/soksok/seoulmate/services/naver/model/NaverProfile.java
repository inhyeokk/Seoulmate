package com.soksok.seoulmate.services.naver.model;

public class NaverProfile {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        private String email;

        private String nickname;

        private String profile_image;

        private String age;

        private String gender;

        private String id;

        private String name;

        private String birthday;

        public Response(String email, String nickname, String profile_image, String age, String gender, String id, String name, String birthday) {
            this.email = email;
            this.nickname = nickname;
            this.profile_image = profile_image;
            this.age = age;
            this.gender = gender;
            this.id = id;
            this.name = name;
            this.birthday = birthday;
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

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }
}
