package com.soksok.seoulmate.view.setting;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.soksok.seoulmate.R;

public class ChangeUserNameDialog extends Dialog {

    public MutableLiveData<String> userName = new MutableLiveData<>();

    public ChangeUserNameDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_user_name);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);

        ImageView closeBtn = findViewById(R.id.iv_close);
        closeBtn.setOnClickListener(v -> dismiss());

        EditText edTitle = findViewById(R.id.ed_user_name);

        TextView tvOk = findViewById(R.id.tv_apply_ok);
        tvOk.setOnClickListener(v -> {
            /** 텍스트 있는 경우만
             *  닉네임 변경 변경
             */
            String title = edTitle.getText().toString();
            if (!title.equals("")) {
                userName.postValue(title);
            }
            dismiss();
        });
    }
}
