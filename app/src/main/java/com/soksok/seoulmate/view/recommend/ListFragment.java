package com.soksok.seoulmate.view.recommend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.soksok.seoulmate.databinding.FragmentListBinding;
import com.soksok.seoulmate.http.model.Recommend;
import com.soksok.seoulmate.view.recommend.adapter.ListAdapter;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ArrayList<Recommend> recommends;

    private FragmentListBinding binding;

    public ListFragment(ArrayList<Recommend> recommends) {
        this.recommends = recommends;
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
        ListAdapter mateAdapter = new ListAdapter(recommends, (v, position) -> {
//            goToExternalBrowser(recommends.get(position).getAddr());
        });
        binding.rcvMate.setAdapter(mateAdapter);
    }

    // 외부 링크 연결
    private void goToExternalBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        getActivity().startActivity(intent);
    }
}
