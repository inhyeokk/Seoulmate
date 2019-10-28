package com.soksok.seoulmate.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.soksok.seoulmate.R;

public class ProgressCircleDialog extends Dialog {

    private static final int SPLASH_TIME_OUT = 1500;
    public MutableLiveData<Boolean> isShowing = new MutableLiveData<>();

    public ProgressCircleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress_circle);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);

        new Handler().postDelayed(this::setIsShowing, SPLASH_TIME_OUT);
    }

    private void setIsShowing() {
        isShowing.postValue(false);
        dismiss();
    }
}