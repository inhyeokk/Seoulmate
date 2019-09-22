package com.soksok.seoulmate.http.model;

/*
   # boolean status
      - 요청성공여부를 표시한다.
      - true 또는 false 로 응답
   # Generic message
      - 응답 데이터가 담긴다.
 */

public class BaseResponse <T> {
    private boolean status;
    private T message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                ", status=" + status +
                ", message=" + message +
                '}';
    }
}
