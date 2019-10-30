package com.soksok.seoulmate.view.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.databinding.ActivityChatBinding;
import com.soksok.seoulmate.http.model.Tour;
import com.soksok.seoulmate.http.model.User;
import com.soksok.seoulmate.services.socket.SocketConst;
import com.soksok.seoulmate.services.socket.SocketHelper;
import com.soksok.seoulmate.view.chat.adapter.ChatAdapter;
import com.soksok.seoulmate.view.chat.adapter.ChatItemListener;
import com.soksok.seoulmate.view.chat.domain.ChatViewModel;
import com.soksok.seoulmate.view.chat.entity.ChatItem;
import com.soksok.seoulmate.view.main.MainActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = ChatActivity.class.getName();
    private static final int REQUEST_PERMISSION = 1001;
    private static final int REQUEST_GALLERY = 5001;

    public static String EXTRA_ALBUM = "EXTRA_ALBUM";
    public static String EXTRA_CHAT_PARTNER_EMAIL = "EXTRA_CHAT_PARTNER_EMAIL";

    private Tour tour;
    private User user;

    private ActivityChatBinding binding;
    private ChatViewModel chatViewModel = new ChatViewModel();

    private ChatAdapter chatAdapter;

    private Socket socket = SocketHelper.getSocket();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        onDataBinding();
        setupViews();
        subscribe();
//        enterChannel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY:
                switch (resultCode) {
                    case RESULT_OK:
                        Uri uri = data.getData();
//                        sendImage(uri);
                        break;
                    case RESULT_CANCELED:
                        // do nothing
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    goToGallery();
                } else {
                    // permission denied
                    // do nothing
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
//        leaveChannel();
        super.onDestroy();
    }

    private void getData() {
        tour = (Tour) getIntent().getSerializableExtra(MainActivity.EXTRA_TOUR);
        user = (User) getIntent().getSerializableExtra(MainActivity.EXTRA_USER);
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
    }

    private void setupViews() {

        if (tour != null && !tour.getName().equals("")) {
            binding.tvTitle.setText(tour.getName());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        binding.rcvChat.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(getChatItems(), new ChatItemListener() {
            @Override
            public void onLayoutClick(View v) {
                onChatClick(v);
            }

            @Override
            public void onProfileClick(View v) {
                goToChatPartnerActivity();
            }
        });
        binding.rcvChat.scrollToPosition(chatAdapter.getItemCount()-1);
        binding.rcvChat.setAdapter(chatAdapter);
        binding.rcvChat.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                binding.rcvChat.postDelayed(() ->
                    binding.rcvChat.scrollToPosition(chatAdapter.getItemCount()-1)
                , 100);
            }
        });

        binding.edMessage.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                case EditorInfo.IME_ACTION_SEND:
//                    attemptSend();
                    break;
                default:
                    break;
            }
            return false;
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_menu, new ChatMenuFragment(tour, new ChatFragmentListener() {
                    @Override
                    public void onShowChatPartnerClick(View v) {
                        goToChatPartnerActivity();
                    }

                    @Override
                    public void onAlbumClick(View v) {
                        goToAlbumActivity();
                    }
                }))
                .commit();
    }

    private ArrayList<ChatItem> getChatItems() {

        ArrayList<ChatItem> items = PrefUtils.getChatItems(user, tour.getIdx());
        if (items == null) {
            items = new ArrayList<>();
        }
        if (items.size() == 0) {
            items.add(new ChatItem(
                    ChatItem.Type.TEMP,
                    BindUtils.getImageMateProfile(tour.getMate()), // 메이트 프로필 이미지
                    getString(R.string.chat_tv_content_partner, user.getNickname(), BindUtils.getMateName(tour.getMate())),
                    BasicUtils.getTime())
            );
        }
        return items;
    }

    /* Socket.io
     * 채팅 채널로 메시지 전송
     */
    private void attemptSend() {

        String msg = binding.edMessage.getText().toString();
        if (!"".equals(msg)) {
            binding.edMessage.setText("");
            addMessage(user.getNickname(), msg, 0);
//            socket.emit("new message", msg);
        }
    }

    /* Socket.io
     * 수신받은 메시지 추가
     */
    private void addMessage(String userName, String msg, int type) {

        if (user.getNickname().equals(userName)) {
            chatAdapter.add(new ChatItem(
                    ChatItem.Type.USER,
                    msg,
                    BasicUtils.getTime()
            ));
        } else {
            chatAdapter.add(new ChatItem(
                    ChatItem.Type.TEMP,
                    BindUtils.getImageMateProfile(tour.getMate()),
                    msg,
                    BasicUtils.getTime()
            ));
        }
        binding.rcvChat.scrollToPosition(chatAdapter.getItemCount()-1);
        PrefUtils.setChatItems(user, tour.getIdx(), chatAdapter.getItems());
    }

//    private void sendMessage() {
//
//        String content = binding.edMessage.getText().toString();
//        if (!content.equals("")) {
//            chatAdapter.add(new ChatItem(ChatItem.Type.USER, content, BasicUtils.getTime()));
//            binding.edMessage.setText("");
//            binding.rcvChat.scrollToPosition(chatAdapter.getItemCount()-1);
//            PrefUtils.setChatItems(user, tour.getIdx(), chatAdapter.getItems());
//        }
//    }

//    private void sendImage(@NotNull Uri uri) {
//
//        String image = BasicUtils.fromURIToBase64(uri);
//        if (!image.equals("")) {
//            ChatItem item = new ChatItem(ChatItem.Type.USER_IMAGE, image, BasicUtils.getTime());
//            chatViewModel.getImageCachePath(item, uri);
//        }
//    }

    private void subscribe() {

        chatViewModel.chatItem.observe(this, chatItem -> {
            // 캐싱된 이미지 주소가 set 된 item add
            chatAdapter.add(chatItem);
            binding.rcvChat.scrollToPosition(chatAdapter.getItemCount()-1);
            PrefUtils.setChatItems(user, tour.getIdx(), chatAdapter.getItems());
        });
    }

    /*
     * 클릭 이벤트
     */
    @Override
    public void onBackPressed() {

        if (binding.dlChat.isDrawerOpen(GravityCompat.END)) {
            binding.dlChat.closeDrawers();
        }  else {
            super.onBackPressed();
        }
    }

    public void onMenuClick(View v) {
        binding.dlChat.openDrawer(GravityCompat.END);
    }

    public void onChatClick(View v) {
        BasicUtils.onCloseKeyboard(binding.edMessage);
    }

    public void onGalleryClick(View v) {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            goToGallery();
        } else {
            requestPermission();
        }
    }

    public void onSendClick(View v) {
        attemptSend();
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSION
        );
    }

    private void goToGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private void goToChatPartnerActivity() {

        Intent intent = new Intent(this, ChatPartnerActivity.class);
        intent.putExtra(EXTRA_CHAT_PARTNER_EMAIL, tour.getMate());
        startActivity(intent);
    }

    private void goToAlbumActivity() {

        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putStringArrayListExtra(ChatActivity.EXTRA_ALBUM, chatAdapter.getImages());
        startActivity(intent);
    }

    /* Socket.io
     * 채널에 진입
     */
//    public void enterChannel() {
//
//        socket.on(Socket.EVENT_CONNECT, onConnect);
//        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
//        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        socket.on(SocketConst.NEW_MESSAGE, onNewMessage);
//        socket.connect();
//        SocketHelper.login();
//    }

//    private void leaveChannel() {
//
//        socket.disconnect();
//        socket.off(Socket.EVENT_CONNECT, onConnect);
//        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
//        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        socket.off(SocketConst.NEW_MESSAGE, onNewMessage);
//    }

//    private Emitter.Listener onConnect = connect ->
//            runOnUiThread(() ->
//                socket.emit(SocketConst.ADD_USER, user.getNickname())
//            );
//
//    private Emitter.Listener onDisconnect = disconnect ->
//        runOnUiThread(() ->
//            Log.e(TAG, "disconnected")
//        );
//
//    private Emitter.Listener onConnectError = error ->
//        runOnUiThread(() -> {
//            Log.e(TAG, "Error connecting");
//            BasicUtils.showToast(getApplicationContext(),
//                    getString(R.string.common_chat_connect_error));
//        });
//
//    private Emitter.Listener onNewMessage = newMessage ->
//        runOnUiThread(() -> {
//            JSONObject data = (JSONObject) newMessage[0];
//            String username;
//            String message;
//            try {
//                username = data.getString("username");
//                message = data.getString("message");
//            } catch (JSONException e) {
//                Log.e(TAG, e.getMessage());
//                return;
//            }
//            /* TODO 메시지 타입
//             * 0: 입력 텍스트
//             * 1: 갤러리 이미지
//             */
//            addMessage(username, message, 0);
//        });


}
