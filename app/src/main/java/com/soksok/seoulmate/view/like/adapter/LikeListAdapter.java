package com.soksok.seoulmate.view.like.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.databinding.ItemLikeListBinding;
import com.soksok.seoulmate.http.model.Recommend;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LikeListAdapter extends RecyclerView.Adapter<LikeListAdapter.LikeListViewHolder> {

    private ArrayList<Recommend> recommends;

    private LikeListItemListener listener;

    public LikeListAdapter(ArrayList<Recommend> recommends, LikeListItemListener listener) {
        this.recommends = recommends;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LikeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemLikeListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_like_list, parent, false);

        LikeListViewHolder viewHolder = new LikeListViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LikeListViewHolder holder, int position) {
        holder.bind(recommends.get(position));
    }

    @Override
    public int getItemCount() {
        return recommends.size();
    }

    public class LikeListViewHolder extends RecyclerView.ViewHolder {

        private ItemLikeListBinding binding;

        public LikeListViewHolder(@NonNull ItemLikeListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NotNull Recommend recommend) {
            /** 이미지 정보 있을 경우 데이터 셋
             */
            if (!recommend.getImage().equals("")) {
                BindUtils.setImageBase64(binding.ivImage, recommend.getImage());
            }
            binding.setHolder(this);
            binding.setRecommend(recommend);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v, getAdapterPosition());
        }
    }
}
