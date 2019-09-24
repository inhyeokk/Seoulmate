package com.soksok.seoulmate.view.match.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.databinding.ItemMateBinding;

import java.util.ArrayList;

public class MateAdapter extends PagerAdapter {

    private ArrayList<Integer> mates;

    public MateAdapter(ArrayList<Integer> mates) {
        this.mates = mates;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ItemMateBinding binding = ItemMateBinding.inflate(
                LayoutInflater.from(container.getContext()), container, false
        );

        binding.setMate(mates.get(position));
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
        return mates.size();
    }

    public void addAll(ArrayList<Integer> mates) {
        this.mates.addAll(mates);
        notifyDataSetChanged();
    }

    public void reset(ArrayList<Integer> mates) {
        this.mates.clear();
        this.mates.addAll(mates);
        notifyDataSetChanged();
    }

    public void clear() {
        mates.clear();
        notifyDataSetChanged();
    }
}
