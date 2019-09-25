package com.soksok.seoulmate.view.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.common.NumberPickerDialog;
import com.soksok.seoulmate.databinding.ActivityJoinBinding;
import com.soksok.seoulmate.view.main.MainActivity;

public class JoinActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1001;
    private static final int REQUEST_GALLERY = 5001;

    private int age = 20;
    private boolean isMale = true;

    private ActivityJoinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join);
    }

    private void setupViews() {

        setAge(age);
        setGender();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY:
                switch (resultCode) {
                    case RESULT_OK:
                        Uri uri = data.getData();
                        BindUtils.setCircleGalleryURI(binding.ivProfile, uri);
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

    private void setAge(int age) {
        this.age = age;
        binding.tvAge.setText(getString(R.string.join_tv_age, age));
    }

    private void setGender() {

        if (isMale) {
            binding.tvMale.setSelected(true);
            binding.tvFemale.setSelected(false);
        } else {
            binding.tvMale.setSelected(false);
            binding.tvFemale.setSelected(true);
        }
    }

    /*
     * 클릭 이벤트
     */
    public void onProfileClick(View v) {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            goToGallery();
        } else {
            requestPermission();
        }
    }

    public void onAgeClick(View v) {

        NumberPickerDialog dialog = new NumberPickerDialog(this, age);
        dialog.show();
        dialog.age.observe(this, this::setAge);
    }

    public void onFemaleClick(View v) {

        isMale = false;
        setGender();
    }

    public void onMaleClick(View v) {

        isMale = true;
        setGender();
    }

    public void onJoinClick(View v) {

        /** 리퀘스트
         *  프로필 이미지: profileImage / Type: String / Base64
         *  이메일:       email        / Type: String
         *  닉네임:       nickname     / Type: String
         *  비밀번호:     password     / Type: String
         *  나이:         age          / Type: int
         *  성별:         isMale       / Type: boolean / true(남성) false(여성)
         */
        String profileImage = BasicUtils.toBase64(binding.ivProfile);
        String email = binding.edEmail.getText().toString();
        String nickname = binding.edEmail.getText().toString();
        String password = binding.edPassword.getText().toString();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSION
        );
    }

    private void goToGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }
}
