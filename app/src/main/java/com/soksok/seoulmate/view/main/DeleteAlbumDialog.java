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
import androidx.lifecycle.MutableLiveData;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.PrefUtils;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.model.request.LoginRequest;
import com.soksok.seoulmate.http.model.request.TourRequest;
import com.soksok.seoulmate.http.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class DeleteAlbumDialog extends Dialog {

    public MutableLiveData<Boolean> isDelete = new MutableLiveData<>();

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
        tvCancel.setOnClickListener(v -> {
            isDelete.postValue(false);
            dismiss();
        });

        TextView tvYes = findViewById(R.id.tv_delete_album_yes);
        tvYes.setOnClickListener(v -> {
            isDelete.postValue(true);
            dismiss();
        });
    }
}
