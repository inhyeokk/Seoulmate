package com.soksok.seoulmate.view.match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.ProgressCircleDialog;
import com.soksok.seoulmate.databinding.ActivityFindMateBinding;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.request.TourRequest;
import com.soksok.seoulmate.http.service.ApiService;
import com.soksok.seoulmate.view.main.ChangeAlbumTitleDialog;
import com.soksok.seoulmate.view.main.MainActivity;
import com.soksok.seoulmate.view.match.adapter.MateAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Mh4-2
 */
public class FindMateActivity extends AppCompatActivity {

    /** MatchActivity 로부터 받은 데이터
     */
    private String firstDateString;
    private String lastDateString;
    private int adultCount;
    private int childCount;
    private int babyCount;

    /** EnterTravelActivity 로부터 받은 데이터
     */
    private String travelTitle;
    private String travelImage;

    private int matePosition = 0;

    private ActivityFindMateBinding binding;

    private MateAdapter mateAdapter;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();


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
        travelTitle = intent.getStringExtra(EnterTravelActivity.EXTRA_TRAVEL_TITLE);
        travelImage = intent.getStringExtra(EnterTravelActivity.EXTRA_TRAVEL_IMAGE);
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_mate);
    }

    private void setupViews() {

        mateAdapter = new MateAdapter(getMates());
        binding.vpMate.setAdapter(mateAdapter);
        binding.vpMate.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                matePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private ArrayList<Integer> getMates() {

        ArrayList<Integer> mates = new ArrayList<>();
        // position 0 ~ 9
        mates.add(R.drawable.ic_mate2);
        mates.add(R.drawable.ic_mate3);
        mates.add(R.drawable.ic_mate4);
        mates.add(R.drawable.ic_mate5);
        mates.add(R.drawable.ic_mate6);
        mates.add(R.drawable.ic_mate7);
        mates.add(R.drawable.ic_mate8);
        mates.add(R.drawable.ic_mate9);
        mates.add(R.drawable.ic_mate10);
        mates.add(R.drawable.ic_mate11);

        return mates;
    }

    /*
     * 클릭 이벤트
     */
    public void onLeftClick(View v) {
        if (matePosition > 0) {
            matePosition -= 1;
            binding.vpMate.setCurrentItem(matePosition);
        }
    }

    public void onRightClick(View v) {
        matePosition += 1;
        binding.vpMate.setCurrentItem(matePosition);
    }

    public void onApplyClick(View v) {
        ApplyMateDialog dialog = new ApplyMateDialog(this);
        dialog.show();

        /*
         * 다이어로그 확인버튼 선택되면 액티비티 종료
         */
        dialog.isShowing.observe(this, isShowing  -> {
            if (!isShowing ) {
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {

        /** 리퀘스트
         *  여행 시작날짜: firstDateString / Type: String / "yyyy-MM-dd HH:mm" 형식
         *  여행 종료날짜: lastDateString / Type: String / "yyyy-MM-dd HH:mm" 형식
         *  성인 동행인원: adultCount / Type: int
         *  어린이 동행인원: childCount / Type: int
         *  유아 동행인원: babyCount / Type: int
         *
         *  여행 제목: travelTitle / Type: String
         *  여행 이미지: travelImage / Type: String / Base64
         *
         *  메이트 번호:     matePosition / Type: int
         *  메이트 이메일:   mateEmail / Type: String / mate0@korea.com
         */

        System.out.println("#goToMainActivity !!!!");
        System.out.println("#firstDateString " +firstDateString);
        System.out.println("#lastDateString " +lastDateString);
        System.out.println("#adultCount " +adultCount);
        System.out.println("#childCount " +childCount);
        System.out.println("#babyCount " +babyCount);
        System.out.println("#travelTitle " +travelTitle);
        System.out.println("#travelImage " +travelImage);
        System.out.println("#matePosition " +matePosition);
        String mateEmail = getString(R.string.common_mate_email, matePosition);
//        String mateEmail = "kys6879@naver.com";

        ApiService apiService = ApiService.retrofit.create(ApiService.class);
        Call<BaseResponse<String>> addTourCall = apiService.addTour(new TourRequest(
                travelTitle,
                firstDateString,
                lastDateString,
                adultCount,childCount,babyCount,
                "경복궁",
                "GG","GG",
                mateEmail,
                travelImage));

        addTourCall.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if(response.code() == 200){
                    // 서버와 통신하여 여행 추가 성공시

                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put(response.body().getMessage(),"");
                    root.updateChildren(map);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    // 그밖에 실패시.
                    System.out.println(response.code());
                    System.out.println(response.errorBody().toString());
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                System.out.println("실패!!");
            }
        });


    }
}
