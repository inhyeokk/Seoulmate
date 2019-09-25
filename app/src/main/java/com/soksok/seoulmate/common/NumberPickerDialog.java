package com.soksok.seoulmate.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.soksok.seoulmate.R;

public class NumberPickerDialog extends Dialog {

    public MutableLiveData<Integer> age = new MutableLiveData<>();

    private int num;

    public NumberPickerDialog(@NonNull Context context, int num) {
        super(context);
        this.num = num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_number_picker);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);

        NumberPicker picker = findViewById(R.id.number_picker);
        picker.setMaxValue(100);
        picker.setMinValue(1);
        picker.setValue(num);
        picker.setWrapSelectorWheel(true);
        picker.setOnValueChangedListener((picker1, oldVal, newVal) -> num = newVal);

        TextView tvOk = findViewById(R.id.tv_apply_ok);
        tvOk.setOnClickListener(v -> {
            age.postValue(num);
            dismiss();
        });
    }
}
