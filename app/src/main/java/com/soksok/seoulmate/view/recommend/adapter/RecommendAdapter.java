package com.soksok.seoulmate.view.recommend.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.soksok.seoulmate.view.recommend.ListFragment;
import com.soksok.seoulmate.view.recommend.MateFragment;

public class RecommendAdapter extends FragmentStatePagerAdapter {

    public RecommendAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MateFragment();

            case 1:
                return new ListFragment();

            case 2:
                return new ListFragment();

            case 3:
                return new ListFragment();

            default:
                throw new IllegalArgumentException("unexpected position: " + position);
        }
    }
}
