package com.soksok.seoulmate.view.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.soksok.seoulmate.databinding.FragmentListBinding;
import com.soksok.seoulmate.view.recommend.adapter.ListAdapter;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(
                LayoutInflater.from(container.getContext()), container, false
        );
        setupView();
        return binding.getRoot();
    }

    private void setupView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvMate.setLayoutManager(layoutManager);
        ListAdapter mateAdapter = new ListAdapter();
        binding.rcvMate.setAdapter(mateAdapter);
    }
}
