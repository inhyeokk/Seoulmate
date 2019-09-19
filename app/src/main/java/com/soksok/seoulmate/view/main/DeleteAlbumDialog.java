package com.soksok.seoulmate.view.main;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.soksok.seoulmate.R;

public class DeleteAlbumDialog extends Dialog {

    public DeleteAlbumDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_album);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);

        TextView tvCancel = findViewById(R.id.tv_delete_album_cancel);
        tvCancel.setOnClickListener(v -> dismiss());

        TextView tvYes = findViewById(R.id.tv_delete_album_yes);
        tvYes.setOnClickListener(v -> {
            // 앨범 삭제
        });
    }
}
