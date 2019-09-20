package com.soksok.seoulmate.view.like.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.databinding.ItemLikeBinding;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {

    private String value;

    public LikeAdapter(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemLikeBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_like, parent, false);

        LikeViewHolder viewHolder = new LikeViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class LikeViewHolder extends RecyclerView.ViewHolder {

        private ItemLikeBinding binding;

        public LikeViewHolder(@NonNull ItemLikeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            BindUtils.setLikeItemTitle(binding.tvTitle, value, "수진");
        }
    }
}
