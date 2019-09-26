package com.soksok.seoulmate.view.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ItemAlbumBinding;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private ArrayList<String> images;

    public AlbumAdapter(ArrayList<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemAlbumBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_album, parent, false);

        AlbumViewHolder viewHolder = new AlbumViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.bind(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        private ItemAlbumBinding binding;

        public AlbumViewHolder(@NonNull ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String image) {
            binding.setImage(image);
            binding.executePendingBindings();
        }
    }
}
