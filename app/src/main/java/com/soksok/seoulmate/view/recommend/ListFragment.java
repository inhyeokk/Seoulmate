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
import com.soksok.seoulmate.view.recommend.adapter.ListItemListener;

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
        ListAdapter mateAdapter = new ListAdapter(recommends, getIsLikes(), new ListItemListener() {
            @Override
            public void onLayoutClick(View v, int position) {
                goToExternalBrowser(recommends.get(position).getUrl());
            }

            @Override
            public void onLikeClick(View v, int position) {
                /* TODO
                 * 좋아요 선택 시 여부 반영
                 */
                System.out.println("like!");
                v.setSelected(!v.isSelected());
            }
        });
        binding.rcvMate.setAdapter(mateAdapter);
    }

    private ArrayList<Boolean> getIsLikes() {
        /* TODO
         * ListAdapter의 recommends의 count와 일치해야함
         * 좋아요 선택 여부를 어디서 처리하실지 몰라서 여기에 임시로 작성해둡니다.
         * Recommend 클래스에서 관리하시면 해당 메소드는 필요없고 ListAdapter의 생성자에
         * isLikes 지워주시고 ViewHolder 내 bind 메소드에 recommend.isLike() 쓰시면됩니다.
         * RecommendActivity에서부터 처리하실거면 ListFragment 생성자에 인자 추가해주세요.
         */
        ArrayList<Boolean> isLikes = new ArrayList<>();
        for (int i = 0; i < recommends.size(); i++) {
            isLikes.add(false);
        }
        return isLikes;
    }

    // 외부 링크 연결
    private void goToExternalBrowser(String url) {

        if (url != null && !url.equals("")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getActivity().startActivity(intent);
        }
    }
}
