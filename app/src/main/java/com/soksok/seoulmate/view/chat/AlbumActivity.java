package com.soksok.seoulmate.view.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.databinding.ActivityAlbumBinding;
import com.soksok.seoulmate.view.chat.adapter.AlbumAdapter;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private ArrayList<String> images = new ArrayList<>();

    private ActivityAlbumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        onDataBinding();
        setupViews();
    }

    private void getData() {
        try {
            images = getIntent().getStringArrayListExtra(ChatActivity.EXTRA_ALBUM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album);
    }

    private void setupViews() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rcvAlbum.setLayoutManager(layoutManager);
        AlbumAdapter albumAdapter = new AlbumAdapter(images);
        binding.rcvAlbum.setAdapter(albumAdapter);
    }
}
