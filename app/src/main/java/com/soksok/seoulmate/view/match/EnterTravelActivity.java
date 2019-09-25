package com.soksok.seoulmate.view.match;

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
import android.view.View;
import android.widget.ImageView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.databinding.ActivityEnterTravelBinding;

/**
 * Mh4-3~12
 */
public class EnterTravelActivity extends AppCompatActivity {

    /** MatchActivity 로부터 받은 데이터
     *  메이트 신청 탭까지 넘겨야함
     */
    private String firstDateString;
    private String lastDateString;
    private int adultCount;
    private int childCount;
    private int babyCount;

    /** EnterTravelActivity 현재 탭 데이터
     * EXTRA_TRAVEL_TITLE: 여행 제목 / Type: String
     * EXTRA_TRAVEL_GALLERY: 여행 이미지 / Type: String
     *
     * 최종 저장을 위해 메이트 신청 탭까지 넘겨야함
     */
    public static String EXTRA_TRAVEL_TITLE = "EXTRA_TRAVEL_TITLE";
    public static String EXTRA_TRAVEL_IMAGE = "EXTRA_TRAVEL_IMAGE";

    private String travelTitle = "";
    private String travelImage = "";

    private static final int REQUEST_PERMISSION = 1001;
    private static final int REQUEST_GALLERY = 5001;

    private ActivityEnterTravelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        onDataBinding();
        setupViews();
    }

    private void getData() {

        Intent intent = getIntent();
        firstDateString = intent.getStringExtra(MatchActivity.EXTRA_FIRST_DATE);
        lastDateString = intent.getStringExtra(MatchActivity.EXTRA_LAST_DATE);
        adultCount = intent.getIntExtra(MatchActivity.EXTRA_ADULT_COUNT, 0);
        childCount = intent.getIntExtra(MatchActivity.EXTRA_CHILD_COUNT, 0);
        babyCount = intent.getIntExtra(MatchActivity.EXTRA_BABY_COUNT, 0);
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_travel);
    }

    private void setupViews() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_GALLERY:
                switch (resultCode) {

                    case RESULT_OK:
                        Uri uri = data.getData();
                        BindUtils.setGalleryURI(binding.ivGallery, uri);
                        binding.ivGallery.setScaleType(ImageView.ScaleType.FIT_XY);
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

    /*
     * 클릭 이벤트
     */
    public void onGalleryClick(View v) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            goToGallery();
        } else {
            requestPermission();
        }
    }

    public void onFindClick(View v) {
        goToFindMateActivity();
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

    private void goToFindMateActivity() {

        Intent intent = new Intent(this, FindMateActivity.class);
        intent.putExtra(MatchActivity.EXTRA_FIRST_DATE, firstDateString);
        intent.putExtra(MatchActivity.EXTRA_LAST_DATE, lastDateString);
        intent.putExtra(MatchActivity.EXTRA_ADULT_COUNT, adultCount);
        intent.putExtra(MatchActivity.EXTRA_CHILD_COUNT, childCount);
        intent.putExtra(MatchActivity.EXTRA_BABY_COUNT, babyCount);

        // 현재 탭 데이터
        travelTitle = binding.edEnterTravelTitle.getText().toString();
        travelImage = BasicUtils.toBase64(binding.ivGallery);
        intent.putExtra(EXTRA_TRAVEL_TITLE, travelTitle);
        intent.putExtra(EXTRA_TRAVEL_IMAGE, travelImage);
        startActivity(intent);
    }
}
