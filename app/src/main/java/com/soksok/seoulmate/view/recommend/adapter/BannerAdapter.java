package com.soksok.seoulmate.view.recommend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.soksok.seoulmate.databinding.ItemBannerBinding;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    private ArrayList<Integer> banners;

    public BannerAdapter(ArrayList<Integer> banners) {
        this.banners = banners;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ItemBannerBinding binding = ItemBannerBinding.inflate(
                LayoutInflater.from(container.getContext()), container, false
        );

        binding.setBanner(banners.get(position));
        binding.executePendingBindings();

        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return banners.size();
    }
}