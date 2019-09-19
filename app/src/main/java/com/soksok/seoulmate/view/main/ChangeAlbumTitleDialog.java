package com.soksok.seoulmate.view.main;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.soksok.seoulmate.R;

public class ChangeAlbumTitleDialog extends Dialog {

    public ChangeAlbumTitleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_album_title);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);

        ImageView closeBtn = findViewById(R.id.iv_close);
        closeBtn.setOnClickListener(v -> dismiss());
    }
}
