package com.soksok.seoulmate.view.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soksok.seoulmate.databinding.FragmentChatMenuBinding;

public class ChatMenuFragment extends Fragment {

    private ChatFragmentListener listener;

    private FragmentChatMenuBinding binding;

    public ChatMenuFragment(ChatFragmentListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatMenuBinding.inflate(
                LayoutInflater.from(container.getContext()), container, false
        );
        binding.setView(this);
        binding.executePendingBindings();
        setupViews();
        return binding.getRoot();
    }

    private void setupViews() {

    }

    /*
     * 클릭 이벤트
     */
    public void onShowChatPartnerClick(View v) {
        listener.onShowChatPartnerClick(v);
    }

    public void onAlbumClick(View v) {
        listener.onAlbumClick(v);
    }
}
