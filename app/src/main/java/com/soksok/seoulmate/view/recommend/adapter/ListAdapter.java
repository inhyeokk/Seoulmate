package com.soksok.seoulmate.view.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ItemListBinding;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

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
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private ItemListBinding binding;

        public ListViewHolder(@NonNull ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {

        }
    }
}
