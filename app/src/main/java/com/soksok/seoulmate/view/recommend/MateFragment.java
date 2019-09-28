package com.soksok.seoulmate.view.recommend;

import android.content.Intent;
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

    public static final String EXTRA_MATE_POSITION = "EXTRA_MATE_POSITION";

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
        ListMateAdapter mateAdapter = new ListMateAdapter(
                getMates(),
                (v, position) -> goToMateActivity(position)
        );
        binding.rcvMate.setAdapter(mateAdapter);
    }

    private ArrayList<Integer> getMates() {

        ArrayList<Integer> mates = new ArrayList<>();
        mates.add(R.drawable.img_list_mate7); // 다은
        mates.add(R.drawable.img_list_mate9); // 수연
        mates.add(R.drawable.img_list_mate1); // 은미
        mates.add(R.drawable.img_list_mate10); // 형규
        mates.add(R.drawable.img_list_mate4); // 원서
        mates.add(R.drawable.img_list_mate8); // 창윤
        mates.add(R.drawable.img_list_mate2); // 은지
        mates.add(R.drawable.img_list_mate3); // 용준
        mates.add(R.drawable.img_list_mate5); // 예린
        mates.add(R.drawable.img_list_mate6); // 유정

        return mates;
    }

    private void goToMateActivity(int position) {
        Intent intent = new Intent(getActivity(), MateActivity.class);
        intent.putExtra(EXTRA_MATE_POSITION, position);
        startActivity(intent);
    }
}
