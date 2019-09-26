package com.soksok.seoulmate.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ItemMyTripBinding;

public class MyTripAdapter extends RecyclerView.Adapter<MyTripAdapter.MyTripViewHolder> {

    private MyTripItemListener listener;

    public MyTripAdapter(MyTripItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemMyTripBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_my_trip, parent, false);

        MyTripViewHolder viewHolder = new MyTripViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTripViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyTripViewHolder extends RecyclerView.ViewHolder {

        private ItemMyTripBinding binding;

        public MyTripViewHolder(@NonNull ItemMyTripBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.setHolder(this);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v, binding.tvTitle.getText().toString());
        }

        public void onMenuClick(View v) {
            listener.onMenuClick(v, getAdapterPosition());
        }
    }
}
