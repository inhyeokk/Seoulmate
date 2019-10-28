package com.soksok.seoulmate.view.like;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ActivityLikeBinding;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.MateMap;
import com.soksok.seoulmate.http.model.Recommend;
import com.soksok.seoulmate.http.service.ApiService;
import com.soksok.seoulmate.view.like.adapter.LikeListAdapter;
import com.soksok.seoulmate.view.like.adapter.LikeListMateAdapter;
import com.soksok.seoulmate.view.recommend.MateActivity;
import com.soksok.seoulmate.view.setting.SettingActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeActivity extends AppCompatActivity {

    public static final String EXTRA_MATE_EMAIL = "EXTRA_MATE_EMAIL";

    private String value;
    private List<String> mateEmails = new ArrayList<>();
    private ArrayList<Recommend> recommends;

    private LikeListMateAdapter likeListMateAdapter;
    private LikeListAdapter likeListAdapter;

    private ActivityLikeBinding binding;

    ApiService apiService = ApiService.retrofit.create(ApiService.class);
    Call<BaseResponse<List<MateMap>>> getMatebyUserCall;
    Call<BaseResponse<List<Recommend>>> getRecommendCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        onDataBinding();
        setupViews();
    }

    private void getData() {
        value = getIntent().getStringExtra(SettingActivity.EXTRA_LIKE);
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_like);
        binding.setValue(value);
    }

    private void setupViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvLike.setLayoutManager(layoutManager);

        // TODO set adapter item
        switch (value) {
            case SettingActivity.VALUE_LIKE_MATE:

                // TODO 좋아요하는 메이트 이메일 데이터 받아옴

                getMatebyUserCall = apiService.getMatebyUser();

                getMatebyUserCall.enqueue(new Callback<BaseResponse<List<MateMap>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<MateMap>>> call, Response<BaseResponse<List<MateMap>>> response) {
                        System.out.println(response.body().getMessage().get(0).getMate_email());
                        String email;
                        int mlike;
                        for(int i =0; i<response.body().getMessage().size(); i++){

                            email = response.body().getMessage().get(i).getMate_email();
                            mlike = response.body().getMessage().get(i).getMlike();

                            if(mlike > 0){
                                mateEmails.add(email);
                            }

                        }
                        if(mateEmails.isEmpty()){
                            System.out.println("isEmpty!!");
                        }
                        likeListMateAdapter = new LikeListMateAdapter(getLikeMates(mateEmails), (v, position) ->
                                goToMateActivity(position)
                        );

                        binding.rcvLike.setAdapter(likeListMateAdapter);
                        setCount(likeListMateAdapter.getItemCount());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<MateMap>>> call, Throwable t) {

                    }
                });

                break;

            case SettingActivity.VALUE_LIKE_SPOT:
                getRecommendCall = apiService.getAttrbyUser();
                bindRecommendData(getRecommendCall);
                break;
            case SettingActivity.VALUE_LIKE_RESTAURANT:
                getRecommendCall = apiService.getEatbyUser();
                bindRecommendData(getRecommendCall);
                break;
            case SettingActivity.VALUE_LIKE_INFORMATION:
                getRecommendCall = apiService.getInfobyUser();
                bindRecommendData(getRecommendCall);
                break;
        }
    }

    private void bindRecommendData(Call<BaseResponse<List<Recommend>>> call){
        recommends = new ArrayList<>();
        call.enqueue(new Callback<BaseResponse<List<Recommend>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Recommend>>> call, Response<BaseResponse<List<Recommend>>> response) {
                for(int i =0 ; i<response.body().getMessage().size(); i++){
                    if(response.body().getMessage().get(i).getTouristMap().getMlike() > 0){
                        recommends.add(response.body().getMessage().get(i));
                    }
                }
                likeListAdapter = new LikeListAdapter(recommends, (v, position) ->
                        goToExternalBrowser(recommends.get(position).getUrl())
                );
                binding.rcvLike.setAdapter(likeListAdapter);
                setCount(likeListAdapter.getItemCount());
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Recommend>>> call, Throwable t) {

            }
        });
    }

    private void setCount(int count) {
        binding.setCount(String.valueOf(count));
        binding.executePendingBindings();
    }

    /**
     * param mateEmails: 메이트 이메일 리스트
     * return mates: 이메일에 맵핑 되어있는 이미지 id
     */
    private ArrayList<Integer> getLikeMates(@NotNull List<String> mateEmails) {

        ArrayList<Integer> mates = new ArrayList<>();
        for (String mateEmail: mateEmails) {
            mates.add(getIdByMateEmail(mateEmail));
        }
        return mates;
    }

    public int getIdByMateEmail(@NotNull String mateEmail) {

        int drawableId = R.drawable.img_list_mate7;
        switch (mateEmail) {
            case "mate0@korea.com": // 다은
                drawableId = R.drawable.img_list_mate7;
                break;
            case "mate1@korea.com": // 수연
                drawableId = R.drawable.img_list_mate9;
                break;
            case "mate2@korea.com": // 은미
                drawableId = R.drawable.img_list_mate1;
                break;
            case "mate3@korea.com": // 형규
                drawableId = R.drawable.img_list_mate10;
                break;
            case "mate4@korea.com": // 원서
                drawableId = R.drawable.bg_mate_tour4;
                break;
            case "mate5@korea.com": // 창윤
                drawableId = R.drawable.img_list_mate8;
                break;
            case "mate6@korea.com": // 은지
                drawableId = R.drawable.img_list_mate2;
                break;
            case "mate7@korea.com": // 용준
                drawableId = R.drawable.img_list_mate3;
                break;
            case "mate8@korea.com": // 예린
                drawableId = R.drawable.img_list_mate5;
                break;
            case "mate9@korea.com": // 유정
                drawableId = R.drawable.img_list_mate6;
                break;
        }
        return drawableId;
    }

    private void goToMateActivity(int position) {
        Intent intent = new Intent(this, MateActivity.class);
        intent.putExtra(EXTRA_MATE_EMAIL, mateEmails.get(position));
        startActivity(intent);
    }

    // 외부 링크 연결
    private void goToExternalBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
