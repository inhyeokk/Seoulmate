package com.soksok.seoulmate.view.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.databinding.FragmentChatMenuBinding;
import com.soksok.seoulmate.http.model.Tour;

import java.util.ArrayList;

public class ChatMenuFragment extends Fragment {

    private Tour tour;
    private ChatFragmentListener listener;

    private int first = 0;
    private int second = 0;
    private int third = 0;
    private int fourth = 0;

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
        initViews();
//        setDaysVisibility();
        setValue();
    }

    private void initViews() {
        BindUtils.setImageTour(binding.ivTour, tour.getMate());
        binding.ivDayFirst.setSelected(true);
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

    private void setValue() {

        first = Character.getNumericValue(tour.getMate().charAt(4));
        second = BasicUtils.getRandomValue(10);
        while (first == second) {
            second = BasicUtils.getRandomValue(10);
        }
        third = BasicUtils.getRandomValue(10);
        while (first == third || second == third) {
            third = BasicUtils.getRandomValue(10);
        }
        fourth = BasicUtils.getRandomValue(10);
        while (first == fourth || second == fourth || third == fourth) {
            fourth = BasicUtils.getRandomValue(10);
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

    public void onDayFirstClick(View v) {

        if (!binding.ivDayFirst.isSelected()) {
            binding.ivDayFirst.setSelected(true);
            binding.ivDaySecond.setSelected(false);
            binding.ivDayThird.setSelected(false);
            binding.ivDayFourth.setSelected(false);
            BindUtils.setImageTour(binding.ivTour, tour.getMate());
        }
    }

    public void onDaySecondClick(View v) {

        if (!binding.ivDaySecond.isSelected()) {
            binding.ivDayFirst.setSelected(false);
            binding.ivDaySecond.setSelected(true);
            binding.ivDayThird.setSelected(false);
            binding.ivDayFourth.setSelected(false);
            BindUtils.setImageTour(binding.ivTour, getString(R.string.common_mate_email, second));
        }
    }

    public void onDayThirdClick(View v) {

        if (!binding.ivDayThird.isSelected()) {
            binding.ivDayFirst.setSelected(false);
            binding.ivDaySecond.setSelected(false);
            binding.ivDayThird.setSelected(true);
            binding.ivDayFourth.setSelected(false);
            BindUtils.setImageTour(binding.ivTour, getString(R.string.common_mate_email, third));
        }
    }

    public void onDayFourthClick(View v) {

        if (!binding.ivDayFourth.isSelected()) {
            binding.ivDayFirst.setSelected(false);
            binding.ivDaySecond.setSelected(false);
            binding.ivDayThird.setSelected(false);
            binding.ivDayFourth.setSelected(true);
            BindUtils.setImageTour(binding.ivTour, getString(R.string.common_mate_email, fourth));
        }
    }
}
