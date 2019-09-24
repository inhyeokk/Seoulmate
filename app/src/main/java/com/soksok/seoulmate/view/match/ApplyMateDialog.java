package com.soksok.seoulmate.view.match;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.soksok.seoulmate.R;

public class ApplyMateDialog extends Dialog {

    public MutableLiveData<Boolean> isShowing = new MutableLiveData<>();

    public ApplyMateDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_apply_mate);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);

        TextView tvOk = findViewById(R.id.tv_apply_ok);
        tvOk.setOnClickListener(v -> {
            isShowing.postValue(false);
            dismiss();
        });
    }
}
