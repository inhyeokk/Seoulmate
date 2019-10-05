package com.soksok.seoulmate.view.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ItemListMateBinding;

import java.util.ArrayList;

public class ListMateAdapter extends RecyclerView.Adapter<ListMateAdapter.ListMateViewHolder> {

    private ArrayList<Integer> mates;
    private ArrayList<Boolean> isLikes;

    private ListItemListener listener;

    public ListMateAdapter(ArrayList<Integer> mates, ArrayList<Boolean> isLikes, ListItemListener listener) {
        this.mates = mates;
        this.isLikes = isLikes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListMateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemListMateBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_list_mate, parent, false);

        ListMateViewHolder viewHolder = new ListMateViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListMateViewHolder holder, int position) {
        holder.bind(mates.get(position), isLikes.get(position));
    }

    @Override
    public int getItemCount() {
        return mates.size();
    }

    public class ListMateViewHolder extends RecyclerView.ViewHolder {

        private ItemListMateBinding binding;

        public ListMateViewHolder(@NonNull ItemListMateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int mate, boolean isLike) {
            binding.setHolder(this);
            binding.setMate(mate);
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
