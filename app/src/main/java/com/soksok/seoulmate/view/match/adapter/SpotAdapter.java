package com.soksok.seoulmate.view.match.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ItemSpotBinding;

public class SpotAdapter extends RecyclerView.Adapter<SpotAdapter.SpotViewHolder> {

    private SpotItemListener listener;

    public SpotAdapter(SpotItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SpotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemSpotBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_spot, parent, false);

        SpotViewHolder viewHolder = new SpotViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpotViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {

        private ItemSpotBinding binding;

        public SpotViewHolder(@NonNull ItemSpotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.setHolder(this);
            binding.executePendingBindings();
        }

        public void onItemClick(View v) {
            listener.onSpotClick(v, getAdapterPosition());
        }
    }
}
