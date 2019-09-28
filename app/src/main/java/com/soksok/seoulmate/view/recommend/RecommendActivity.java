package com.soksok.seoulmate.view.recommend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ActivityRecommendBinding;
import com.soksok.seoulmate.http.model.Recommend;
import com.soksok.seoulmate.view.recommend.adapter.BannerAdapter;
import com.soksok.seoulmate.view.recommend.adapter.RecommendAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RecommendActivity extends AppCompatActivity {

    private Timer timer;

    private ActivityRecommendBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend);
    }

    private void setupViews() {

        BannerAdapter bannerAdapter = new BannerAdapter(getBanners());
        binding.vpRecommend.setAdapter(bannerAdapter);
        setAutoTimer();

        RecommendAdapter recommendAdapter = new RecommendAdapter(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                getSpots(),
                getRestaurants(),
                getInformations()
        );

        binding.vpList.setAdapter(recommendAdapter);
        binding.vpList.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vpList.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private ArrayList<Recommend> getSpots() {

        ArrayList<Recommend> spots = new ArrayList<>();

        spots.add(new Recommend(
                "",
                0,
                "청계천",
                "서울시 종로구 무교로",
                "연등,서울축제,문화",
                "")
        );

        return spots;
    }

    private ArrayList<Recommend> getRestaurants() {

        ArrayList<Recommend> restaurants = new ArrayList<>();

        restaurants.add(new Recommend(
                "",
                0,
                "강식당",
                "서울시 용산구",
                "연등,서울축제,문화",
                "")
        );

        return restaurants;
    }

    private ArrayList<Recommend> getInformations() {

        ArrayList<Recommend> restaurants = new ArrayList<>();

        restaurants.add(new Recommend(
                "",
                0,
                "서울 방문",
                "서울 방문에 관한 모든 것",
                "#비자 #출입국",
                "")
        );

        return restaurants;
    }

    private ArrayList<Integer> getBanners() {

        ArrayList<Integer> banners = new ArrayList<>();
        banners.add(R.drawable.img_recommend1);
        banners.add(R.drawable.img_recommend2);
        banners.add(R.drawable.img_recommend3);

        return banners;
    }

    private void setAutoTimer() {

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(RecommendActivity.this::setAutoSwipe);
            }
        };
        timer.schedule(task, 5000, 5000);
    }

    private void setAutoSwipe() {

        int bannerPosition = binding.vpRecommend.getCurrentItem();
        bannerPosition += 1;
        if (bannerPosition == 3) {
            bannerPosition = 0;
        }
        binding.vpRecommend.setCurrentItem(bannerPosition, true);
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}
