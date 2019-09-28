package com.soksok.seoulmate.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BindUtils;
import com.soksok.seoulmate.databinding.ItemMyTripBinding;
import com.soksok.seoulmate.http.model.Tour;

import java.util.ArrayList;

public class MyTripAdapter extends RecyclerView.Adapter<MyTripAdapter.MyTripViewHolder> {

    private ArrayList<Tour> tours;

    private MyTripItemListener listener;

    public MyTripAdapter(ArrayList<Tour> tours, MyTripItemListener listener) {
        this.tours = tours;
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
        holder.bind(tours.get(position));
    }

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public class MyTripViewHolder extends RecyclerView.ViewHolder {

        private Tour tour;
        private ItemMyTripBinding binding;

        public MyTripViewHolder(@NonNull ItemMyTripBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Tour tour) {

            this.tour = tour;

            /** 여행 이미지, 메이트 정보 있을 경우 데이터 셋
             */
            if (!tour.getImage().equals("")) {
                BindUtils.setImageBase64(binding.ivImage, tour.getImage());
            }
            if (!tour.getMate().equals("")) {
                BindUtils.setImageMateProfile(binding.civProfile, tour.getMate());
            }

            binding.setHolder(this);
            binding.setTour(tour);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v, tour);
        }

        public void onMenuClick(View v) {
            listener.onMenuClick(v, getAdapterPosition());
        }
    }
}
