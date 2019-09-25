package com.soksok.seoulmate.view.match.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.databinding.ItemListSpotBinding;
import com.soksok.seoulmate.databinding.ItemSpotBinding;

import java.util.ArrayList;

public class SpotAdapter extends RecyclerView.Adapter<SpotAdapter.SpotViewHolder> {

    private ArrayList<Integer> spots;
    private SpotItemListener listener;

    public SpotAdapter(ArrayList<Integer> spots, SpotItemListener listener) {
        this.spots = spots;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SpotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemListSpotBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_list_spot, parent, false);

        SpotViewHolder viewHolder = new SpotViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpotViewHolder holder, int position) {
        holder.bind(spots.get(position));
    }

    @Override
    public int getItemCount() {
        return spots.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {

        private ItemListSpotBinding binding;

        public SpotViewHolder(@NonNull ItemListSpotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int spot) {
            binding.setHolder(this);
//            binding.setSpot(spot);
            binding.ivSpot.setBackgroundResource(spot);
            binding.executePendingBindings();
        }

        public void onItemClick(View v) {
            listener.onSpotClick(v, getAdapterPosition());
        }
    }
}
