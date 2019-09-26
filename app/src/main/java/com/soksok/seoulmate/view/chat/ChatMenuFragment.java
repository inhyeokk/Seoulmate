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

    private FragmentChatMenuBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatMenuBinding.inflate(
                LayoutInflater.from(container.getContext()), container, false
        );
        setupViews();
        return binding.getRoot();
    }

    private void setupViews() {

    }
}
