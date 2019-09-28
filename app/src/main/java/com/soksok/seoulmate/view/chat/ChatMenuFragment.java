package com.soksok.seoulmate.view.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.databinding.FragmentChatMenuBinding;
import com.soksok.seoulmate.http.model.Tour;

import java.util.ArrayList;

public class ChatMenuFragment extends Fragment {

    private Tour tour;
    private ChatFragmentListener listener;

    private FragmentChatMenuBinding binding;

    public ChatMenuFragment(Tour tour, ChatFragmentListener listener) {
        this.tour = tour;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatMenuBinding.inflate(
                LayoutInflater.from(container.getContext()), container, false
        );
        binding.setView(this);
        binding.executePendingBindings();
        setupViews();
        return binding.getRoot();
    }

    private void setupViews() {
        setDaysVisibility();
        setDaysGone();
    }

    private void setDaysVisibility() {

        ArrayList<Integer> startDate = BasicUtils.getDateTime(tour.getStart_date());
        ArrayList<Integer> endDate = BasicUtils.getDateTime(tour.getEnd_date());

        int difference = 0;
        if (startDate.get(0).equals(endDate.get(0))) {
            if (startDate.get(1).equals(endDate.get(1))) {
                difference = endDate.get(2) - startDate.get(2);
            }
        }

        if (difference <= 0) {
            binding.ivDaySecond.setVisibility(View.INVISIBLE);
            binding.ivDayThird.setVisibility(View.INVISIBLE);
            binding.ivDayFourth.setVisibility(View.INVISIBLE);
        } else if (difference == 1) {
            binding.ivDayThird.setVisibility(View.INVISIBLE);
            binding.ivDayFourth.setVisibility(View.INVISIBLE);
        } else if (difference == 2) {
            binding.ivDayFourth.setVisibility(View.INVISIBLE);
        }
    }

    private void setDaysGone() {

        ArrayList<Integer> currentDate = BasicUtils.getDate();
        ArrayList<Integer> startDate = BasicUtils.getDateTime(tour.getStart_date());

        int difference = -1;
        if (currentDate.get(0).equals(startDate.get(0))) {
            if (currentDate.get(1).equals(startDate.get(1))) {
                difference = currentDate.get(2) - startDate.get(2);
            } else if (currentDate.get(1) > startDate.get(1)) {
                difference = 30;
            }
        }

        if (difference == 0) {
            binding.ivDayFirst.setSelected(true);
        } else if (difference == 1) {
            binding.ivDayFirst.setSelected(true);
            binding.ivDaySecond.setSelected(true);
        } else if (difference == 2) {
            binding.ivDayFirst.setSelected(true);
            binding.ivDaySecond.setSelected(true);
            binding.ivDayThird.setSelected(true);
        } else if (difference >= 3) {
            binding.ivDayFirst.setSelected(true);
            binding.ivDaySecond.setSelected(true);
            binding.ivDayThird.setSelected(true);
            binding.ivDayFourth.setSelected(true);
        }
    }

    /*
     * 클릭 이벤트
     */
    public void onShowChatPartnerClick(View v) {
        listener.onShowChatPartnerClick(v);
    }

    public void onAlbumClick(View v) {
        listener.onAlbumClick(v);
    }
}
