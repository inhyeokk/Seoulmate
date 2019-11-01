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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListMateAdapter extends RecyclerView.Adapter<ListMateAdapter.ListMateViewHolder> {

    private ArrayList<Integer> mates = new ArrayList<>();
    private ArrayList<Boolean> isLikes;

    private ListItemListener listener;

    public ListMateAdapter(ArrayList<Integer> mates , ArrayList<Boolean> isLikes, ListItemListener listener) {
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
        holder.bind(position, mates.get(position), isLikes.get(position));
    }

    @Override
    public int getItemCount() {
        return mates.size();
    }

    public void setIsLikeByPosition(int position, boolean isLike) {
        isLikes.set(position, isLike);
    }

    public class ListMateViewHolder extends RecyclerView.ViewHolder {

        private int position = getAdapterPosition();
        private ItemListMateBinding binding;

        public ListMateViewHolder(@NonNull ItemListMateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, int mate, boolean isLike) {
            this.position = position;
            binding.setHolder(this);
            binding.setMate(mate);
            binding.ivLike.setSelected(isLike);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v, position);
        }

        public void onLikeClick(@NotNull View v) {
            setIsLikeByPosition(position, !v.isSelected());
            listener.onLikeClick(v, position);
        }
    }
}
