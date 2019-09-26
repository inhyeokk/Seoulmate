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
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.databinding.ActivityChatBinding;
import com.soksok.seoulmate.view.chat.adapter.ChatAdapter;
import com.soksok.seoulmate.view.chat.adapter.ChatItemListener;
import com.soksok.seoulmate.view.chat.entity.ChatItem;
import com.soksok.seoulmate.view.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1001;
    private static final int REQUEST_GALLERY = 5001;

    private String title = "";

    private ActivityChatBinding binding;

    private ChatAdapter chatAdapter;

    private ChatMenuFragment menuFragment = new ChatMenuFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        onDataBinding();
        setupViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_GALLERY:
                switch (resultCode) {

                    case RESULT_OK:
                        Uri uri = data.getData();
                        sendImage(BasicUtils.fromURIToBase64(uri));
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

    private void getData() {
        title = getIntent().getStringExtra(MainActivity.EXTRA_CHAT_TITLE);
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
    }

    private void setupViews() {

        if (title != null && !title.equals("")) {
            binding.tvTitle.setText(title);
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
            public void onProfileClick(View v, int position) {

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
                    sendMessage();
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    private ArrayList<ChatItem> getChatItems() {

        ArrayList<ChatItem> items = new ArrayList<>();

        items.add(new ChatItem(
                ChatItem.Type.TEMP,
                R.drawable.ic_profile_mate,
                "hello breeze! welcome to seoul",
                "10:24")
        );

        items.add(new ChatItem(
                ChatItem.Type.USER,
                "hello sujin~",
                "10:31")
        );

        items.add(new ChatItem(
                ChatItem.Type.TEMP,
                R.drawable.ic_profile_mate,
                "Can you give me a common mailbox? I will send you a road map.",
                "10:37")
        );

        items.add(new ChatItem(
                ChatItem.Type.USER,
                "breeze0908@gmail.com",
                "10:39")
        );

        items.add(new ChatItem(
                ChatItem.Type.USER,
                "please ;-)",
                "10:39")
        );

        return items;
    }

    private void sendMessage() {

        String content = binding.edMessage.getText().toString();
        if (!content.equals("")) {
            chatAdapter.add(new ChatItem(ChatItem.Type.USER, content, BasicUtils.getTime()));
            binding.edMessage.setText("");
            binding.rcvChat.scrollToPosition(chatAdapter.getItemCount()-1);
        }
    }

    private void sendImage(@NotNull String image) {

        if (!image.equals("")) {
            chatAdapter.add(new ChatItem(ChatItem.Type.USER_IMAGE, image, BasicUtils.getTime()));
            binding.rcvChat.scrollToPosition(chatAdapter.getItemCount()-1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_menu, menuFragment)
                .commit();
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
        sendMessage();
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
}
