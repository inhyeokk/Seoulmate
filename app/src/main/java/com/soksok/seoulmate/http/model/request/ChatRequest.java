package com.soksok.seoulmate.http.model.request;

public class ChatRequest {
    String reciver;
    String message;

    public ChatRequest(String reciver, String message) {
        this.reciver = reciver;
        this.message = message;
    }
}
