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
import com.soksok.seoulmate.http.model.Recommend;
import com.soksok.seoulmate.view.like.adapter.LikeListAdapter;
import com.soksok.seoulmate.view.like.adapter.LikeListMateAdapter;
import com.soksok.seoulmate.view.recommend.MateActivity;
import com.soksok.seoulmate.view.setting.SettingActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LikeActivity extends AppCompatActivity {

    public static final String EXTRA_MATE_EMAIL = "EXTRA_MATE_EMAIL";

    private String value;
    private ArrayList<String> mateEmails;
    private ArrayList<Recommend> recommends;

    private LikeListMateAdapter likeListMateAdapter;
    private LikeListAdapter likeListAdapter;

    private ActivityLikeBinding binding;

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
                mateEmails = new ArrayList<>();
                mateEmails.add("mate0@korea.com");
                mateEmails.add("mate3@korea.com");
                mateEmails.add("mate5@korea.com");

                likeListMateAdapter = new LikeListMateAdapter(getLikeMates(mateEmails), (v, position) ->
                    goToMateActivity(position)
                );
                binding.rcvLike.setAdapter(likeListMateAdapter);
                setCount(likeListMateAdapter.getItemCount());
                break;

            case SettingActivity.VALUE_LIKE_SPOT:
            case SettingActivity.VALUE_LIKE_RESTAURANT:
            case SettingActivity.VALUE_LIKE_INFORMATION:

                // TODO 좋아요하는 맛집 or 관광지 or 정보 데이터 받아옴
                recommends = new ArrayList<>();

                likeListAdapter = new LikeListAdapter(recommends, (v, position) ->
                    goToExternalBrowser(recommends.get(position).getUrl())
                );
                binding.rcvLike.setAdapter(likeListAdapter);
                setCount(likeListAdapter.getItemCount());
                break;
        }
    }

    private void setCount(int count) {
        binding.setCount(String.valueOf(count));
        binding.executePendingBindings();
    }

    /**
     * param mateEmails: 메이트 이메일 리스트
     * return mates: 이메일에 맵핑 되어있는 이미지 id
     */
    private ArrayList<Integer> getLikeMates(@NotNull ArrayList<String> mateEmails) {

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
