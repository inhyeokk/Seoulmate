package com.soksok.seoulmate.view.like.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ItemLikeListMateBinding;
import com.soksok.seoulmate.databinding.ItemListMateBinding;

import java.util.ArrayList;

public class LikeListMateAdapter extends RecyclerView.Adapter<LikeListMateAdapter.LikeListMateViewHolder> {

    private ArrayList<Integer> mates;

    private LikeListItemListener listener;

    public LikeListMateAdapter(ArrayList<Integer> mates, LikeListItemListener listener) {
        this.mates = mates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LikeListMateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemLikeListMateBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_like_list_mate, parent, false);

        LikeListMateViewHolder viewHolder = new LikeListMateViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LikeListMateViewHolder holder, int position) {
        holder.bind(mates.get(position));
    }

    @Override
    public int getItemCount() {
        return mates.size();
    }

    public class LikeListMateViewHolder extends RecyclerView.ViewHolder {

        private ItemLikeListMateBinding binding;

        public LikeListMateViewHolder(@NonNull ItemLikeListMateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.setHolder(this);
            binding.setMate(position);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v, getAdapterPosition());
        }
    }
}
