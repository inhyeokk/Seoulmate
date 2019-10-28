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
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.Recommend;
import com.soksok.seoulmate.http.service.ApiService;
import com.soksok.seoulmate.view.recommend.adapter.ListAdapter;
import com.soksok.seoulmate.view.recommend.adapter.ListItemListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    private ArrayList<Recommend> recommends;
    private int kind;

    private FragmentListBinding binding;

    ApiService apiService = ApiService.retrofit.create(ApiService.class);
    Call<BaseResponse<List<Recommend>>> getTouristCall;
    Call<BaseResponse<List<String>>> likeCall;
    Call<BaseResponse<List<String>>> unlikeCall;

    ArrayList<Boolean> isLikes = new ArrayList<>();

    public ListFragment(ArrayList<Recommend> recommends , int kind ) {
        this.recommends = recommends;
        this.kind = kind;
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

        if(kind == 1){ // 관광지
            getTouristCall = apiService.getAttrbyUser();
        } else if(kind == 2){ // 맛집
            getTouristCall = apiService.getEatbyUser();
        }else if(kind ==3){ // 정보
            getTouristCall = apiService.getInfobyUser();
        }



        getTouristCall.enqueue(new Callback<BaseResponse<List<Recommend>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Recommend>>> call, Response<BaseResponse<List<Recommend>>> response) {
                System.out.println(response.body().getMessage().size());



                for(int i=0; i<response.body().getMessage().size(); i++){
                    if(response.body().getMessage().get(i).getTouristMap().getMlike() < 1){
                        isLikes.add(false);
                    } else {
                        isLikes.add(true);
                    }
                }

                ListAdapter mateAdapter = new ListAdapter(recommends, isLikes, new ListItemListener() {
                    @Override
                    public void onLayoutClick(View v, int position) {
                        goToExternalBrowser(recommends.get(position).getUrl());
                    }

                    @Override
                    public void onLikeClick(View v, int position) {
                        /* TODO
                         * 좋아요 선택 시 여부 반영
                         */
                        v.setSelected(!v.isSelected());
                        boolean isSel = v.isSelected();
                        if(isSel){
                            likeCall = apiService.likeRecommend( Integer.toString(kind), recommends.get(position).getNum());
                            likeCall.enqueue(new Callback<BaseResponse<List<String>>>() {
                                @Override
                                public void onResponse(Call<BaseResponse<List<String>>> call, Response<BaseResponse<List<String>>> response) {

                                }

                                @Override
                                public void onFailure(Call<BaseResponse<List<String>>> call, Throwable t) {

                                }
                            });
                        } else {
                            unlikeCall = apiService.unlikeRecommend( Integer.toString(kind), recommends.get(position).getNum());
                            unlikeCall.enqueue(new Callback<BaseResponse<List<String>>>() {
                                @Override
                                public void onResponse(Call<BaseResponse<List<String>>> call, Response<BaseResponse<List<String>>> response) {

                                }

                                @Override
                                public void onFailure(Call<BaseResponse<List<String>>> call, Throwable t) {

                                }
                            });
                        }


                        System.out.println("like! " + recommends.get(position).getClassname());
                    }
                });
                binding.rcvMate.setAdapter(mateAdapter);


            }

            @Override
            public void onFailure(Call<BaseResponse<List<Recommend>>> call, Throwable t) {

            }
        });



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
            System.out.println(recommends.size());
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