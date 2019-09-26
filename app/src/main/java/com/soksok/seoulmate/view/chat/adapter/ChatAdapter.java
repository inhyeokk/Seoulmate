package com.soksok.seoulmate.view.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ItemChatPartnerBinding;
import com.soksok.seoulmate.databinding.ItemChatTempBinding;
import com.soksok.seoulmate.databinding.ItemChatUserBinding;
import com.soksok.seoulmate.databinding.ItemChatUserImageBinding;
import com.soksok.seoulmate.view.chat.entity.ChatItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatItem> items;
    private final ChatItemListener listener;

    public ChatAdapter(ArrayList<ChatItem> items, ChatItemListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType) { // user: 0, partner: 1, temp: 2, user_image: 3
            case 0:
                ItemChatUserBinding userBinding = DataBindingUtil.inflate(inflater, R.layout.item_chat_user, parent, false);
                return new ChatUserViewHolder(userBinding);
            case 1:
                ItemChatPartnerBinding partnerBinding = DataBindingUtil.inflate(inflater, R.layout.item_chat_partner, parent, false);
                return new ChatPartnerViewHolder(partnerBinding);
            case 2:
                ItemChatTempBinding tempBinding = DataBindingUtil.inflate(inflater, R.layout.item_chat_temp, parent, false);
                return new ChatTempViewHolder(tempBinding);
            case 3:
                ItemChatUserImageBinding userImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_chat_user_image, parent, false);
                return new ChatUserImageViewHolder(userImageBinding);
            default:
                throw new IllegalArgumentException("unexpected viewType: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) { // user: 0, partner: 1, temp: 2, user_image: 3
            case 0:
                onBindChatUserViewHolder((ChatUserViewHolder) holder, position);
                break;
            case 1:
                onBindChatPartnerViewHolder((ChatPartnerViewHolder) holder, position);
                break;
            case 2:
                onBindChatTempViewHolder((ChatTempViewHolder) holder, position);
                break;
            case 3:
                onBindChatUserImageViewHolder((ChatUserImageViewHolder) holder, position);
                break;
        }
    }

    private void onBindChatUserViewHolder(@NotNull ChatUserViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    private void onBindChatPartnerViewHolder(@NotNull ChatPartnerViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    private void onBindChatTempViewHolder(@NotNull ChatTempViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    private void onBindChatUserImageViewHolder(@NotNull ChatUserImageViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType().getValue();
    }

    public void add(ChatItem item) {
        items.add(item);
        notifyItemInserted(items.size()-1);
    }

    public void addAll(ArrayList<ChatItem> items) {
        int oldSize = getItemCount();
        this.items.addAll(items);
        notifyItemRangeInserted(oldSize, items.size());
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public class ChatUserViewHolder extends RecyclerView.ViewHolder {

        private ItemChatUserBinding binding;

        public ChatUserViewHolder(@NonNull ItemChatUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatItem item) {
            binding.setHolder(this);
            binding.setItem(item);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v);
        }
    }

    public class ChatPartnerViewHolder extends RecyclerView.ViewHolder {

        private ItemChatPartnerBinding binding;

        public ChatPartnerViewHolder(@NonNull ItemChatPartnerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatItem item) {
            binding.setHolder(this);
            binding.setItem(item);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v);
        }
    }

    public class ChatTempViewHolder extends RecyclerView.ViewHolder {

        private ItemChatTempBinding binding;

        public ChatTempViewHolder(@NonNull ItemChatTempBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatItem item) {
            binding.setHolder(this);
            binding.setItem(item);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v);
        }
    }

    public class ChatUserImageViewHolder extends RecyclerView.ViewHolder {

        private ItemChatUserImageBinding binding;

        public ChatUserImageViewHolder(@NonNull ItemChatUserImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatItem item) {
            binding.setHolder(this);
            binding.setItem(item);
            binding.executePendingBindings();
        }

        public void onLayoutClick(View v) {
            listener.onLayoutClick(v);
        }
    }
}
