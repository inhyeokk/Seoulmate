package com.soksok.seoulmate.services.socket;

import android.text.TextUtils;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketHelper {

    private static final String TAG = SocketHelper.class.getName();
    private static Socket socket;

    /* "SeoulmateApplication"에서 초기화
     */
    public static void init() {
        try {
            // TODO URL 변경
            socket = IO.socket(SocketConst.TEST_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        return socket;
    }

    /* MainActivity 진입 시 로그인 시도
     */
    public static void attemptLogin(String id) {

        if (TextUtils.isEmpty(id)) {
            return;
        }
        socket.emit(SocketConst.ADD_USER, id);
    }

    /* 로그인에 성공한 뒤 "ChatActivity"에서 사용
     * 채팅 채널에 접속
     */
    public static void login() {
        socket.on(SocketConst.LOGIN, onLogin);
    }

    /* 앱 종료 시 로그아웃
     */
    public static void logout() {
        socket.off(SocketConst.LOGIN, onLogin);
    }

    private static Emitter.Listener onLogin = login -> {

        JSONObject data = (JSONObject) login[0];
        /* 유저가 들어온 경우 or 나간 경우
         * "username, numUsers"로 구현할 수 있음
         */
    };
}