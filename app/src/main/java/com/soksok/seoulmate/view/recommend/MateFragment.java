package com.soksok.seoulmate.view.recommend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.FragmentMateBinding;
import com.soksok.seoulmate.view.recommend.adapter.ListMateAdapter;

import java.util.ArrayList;

public class MateFragment extends Fragment {

    private FragmentMateBinding binding;

    public MateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMateBinding.inflate(
                LayoutInflater.from(container.getContext()), container, false
        );
        setupView();
        return binding.getRoot();
    }

    private void setupView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvMate.setLayoutManager(layoutManager);
        ListMateAdapter mateAdapter = new ListMateAdapter(getMates());
        binding.rcvMate.setAdapter(mateAdapter);
    }

    private ArrayList<Integer> getMates() {

        ArrayList<Integer> mates = new ArrayList<>();
        mates.add(R.drawable.img_list_mate1);
        mates.add(R.drawable.img_list_mate2);
        mates.add(R.drawable.img_list_mate3);
        mates.add(R.drawable.img_list_mate4);
        mates.add(R.drawable.img_list_mate5);
        mates.add(R.drawable.img_list_mate6);
        mates.add(R.drawable.img_list_mate7);
        mates.add(R.drawable.img_list_mate8);
        mates.add(R.drawable.img_list_mate9);
        mates.add(R.drawable.img_list_mate10);

        return mates;
    }
}
