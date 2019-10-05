package com.soksok.seoulmate.view.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.databinding.ItemListBinding;
import com.soksok.seoulmate.http.model.Recommend;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ArrayList<Recommend> recommends;
    private ArrayList<Boolean> isLikes;

    private ListItemListener listener;

    public ListAdapter(ArrayList<Recommend> recommends, ArrayList<Boolean> isLikes, ListItemListener listener) {
        this.recommends = recommends;
        this.isLikes = isLikes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_list, parent, false);

        ListViewHolder viewHolder = new ListViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(recommends.get(position), isLikes.get(position));
    }

    @Override
    public int getItemCount() {
        return recommends.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private ItemListBinding binding;

        public ListViewHolder(@NonNull ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NotNull Recommend recommend, boolean isLike) {
            /** 이미지 정보 있을 경우 데이터 셋
             */
            if (!recommend.getImage().equals("")) {
                BindUtils.setImageBase64(binding.ivImage, recommend.getImage());
            }
            binding.setHolder(this);
            binding.setRecommend(recommend);
            binding.ivLike.setSelected(isLike);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v, getAdapterPosition());
        }

        public void onLikeClick(View v) {
            listener.onLikeClick(v, getAdapterPosition());
        }
    }
}
