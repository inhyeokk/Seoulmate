package com.soksok.seoulmate.view.main;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.soksok.seoulmate.R;

public class ChangeAlbumTitleDialog extends Dialog {

    public MutableLiveData<String> albumTitle = new MutableLiveData<>();

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

        EditText edTitle = findViewById(R.id.ed_album_title);

        TextView tvOk = findViewById(R.id.tv_apply_ok);
        tvOk.setOnClickListener(v -> {
            /** 텍스트 있는 경우만
             *  앨범 제목 변경
             */
            String title = edTitle.getText().toString();
            if (!title.equals("")) {
                albumTitle.postValue(title);
            }
            dismiss();
        });
    }
}
