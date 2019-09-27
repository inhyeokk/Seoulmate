package com.soksok.seoulmate.view.setting;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.soksok.seoulmate.R;

public class LogoutDialog extends Dialog {

    public MutableLiveData<Boolean> isLogout = new MutableLiveData<>();

    public LogoutDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_logout);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);

        TextView tvCancel = findViewById(R.id.tv_logout_cancel);
        tvCancel.setOnClickListener(v -> {
            isLogout.postValue(false);
            dismiss();
        });

        TextView tvYes = findViewById(R.id.tv_logout_yes);
        tvYes.setOnClickListener(v -> isLogout.postValue(true));
    }
}
