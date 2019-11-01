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
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.MateMap;
import com.soksok.seoulmate.http.service.ApiService;
import com.soksok.seoulmate.view.recommend.adapter.ListItemListener;
import com.soksok.seoulmate.view.recommend.adapter.ListMateAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MateFragment extends Fragment {

    public static final String EXTRA_MATE_EMAIL = "EXTRA_MATE_EMAIL";

    private FragmentMateBinding binding;

    Call<BaseResponse<String>> likeMateCall;
    Call<BaseResponse<String>> unlikeMateCall;
    Call<BaseResponse<List<MateMap>>> getMatebyUserCall;

    ApiService apiService = ApiService.retrofit.create(ApiService.class);

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

        System.out.println("접근!!");

        ArrayList<Boolean> isLikes = new ArrayList<>();
        getMatebyUserCall = apiService.getMatebyUser();
        System.out.println("접근!!");
        getMatebyUserCall.enqueue(new Callback<BaseResponse<List<MateMap>>>() {

            @Override
            public void onResponse(Call<BaseResponse<List<MateMap>>> call, Response<BaseResponse<List<MateMap>>> response) {

                for(int i=0; i<response.body().getMessage().size(); i++){
                    if(response.body().getMessage().get(i).getMlike() < 1){
                        isLikes.add(false);
                    } else {
                        isLikes.add(true);
                    }
                }

                final ListMateAdapter mateAdapter = new ListMateAdapter(getMates(),isLikes, new ListItemListener() {
                    @Override
                    public void onLayoutClick(View v, int position) {
                        goToMateActivity(getString(R.string.common_mate_email, position));
                    }

                    @Override
                    public void onLikeClick(View v, int position) {
                        /* TODO
                         * 좋아요 선택 시 여부 반영
                         */
                        v.setSelected(!v.isSelected());

                        System.out.println("mate like : " +position);
                        System.out.println("isSelected : " +v.isSelected());
                        boolean isSel = v.isSelected();

                        if(isSel){
                            // 그 메이트에게 좋아요
                            System.out.println("like email --> " + String.format("mate%d@korea.com",position));
                            likeMateCall = apiService.likeMate(String.format("mate%d@korea.com",position));
                            likeMateCall.enqueue(new Callback<BaseResponse<String>>() {
                                @Override
                                public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                                }

                                @Override
                                public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

                                }
                            });

                        } else {
                            // 그 메이트에게 좋아요 취소
                            unlikeMateCall = apiService.unlikeMate(String.format("mate%d@korea.com",position));
                            unlikeMateCall.enqueue(new Callback<BaseResponse<String>>() {
                                @Override
                                public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                                }

                                @Override
                                public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
                binding.rcvMate.setAdapter(mateAdapter);
            }
            @Override
            public void onFailure(Call<BaseResponse<List<MateMap>>> call, Throwable t) {

            }
        });
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

    private ArrayList<Boolean> getIsLikes() {

        /* TODO
         * ListMateAdapter의 mates의 count와 일치해야함
         */
        ArrayList<Boolean> isLikes = new ArrayList<>();


//        isLikes.add(true); // 다은
//        isLikes.add(false); // 수연
//        isLikes.add(true); // 은미
//        isLikes.add(true); // 형규
//        isLikes.add(false); // 원서
//        isLikes.add(false); // 창윤
//        isLikes.add(false); // 은지
//        isLikes.add(false); // 용준
//        isLikes.add(false); // 예린
//        isLikes.add(false); // 유정
        return isLikes;
    }

    private void goToMateActivity(String mateEmail) {
        Intent intent = new Intent(getActivity(), MateActivity.class);
        intent.putExtra(EXTRA_MATE_EMAIL, mateEmail);
        startActivity(intent);
    }
}
