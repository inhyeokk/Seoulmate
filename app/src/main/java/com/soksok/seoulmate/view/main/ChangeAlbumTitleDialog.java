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
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.http.model.BaseResponse;
import com.soksok.seoulmate.http.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            if (title.equals("")) {
                System.out.println("title 안씀");
                dismiss();
                return ;
            }
//            albumTitle.postValue(title);

            ApiService apiService = ApiService.retrofit.create(ApiService.class);
            Call<BaseResponse<String>> updateTitleTour = apiService.updateTitleTour(title, "1234");

            updateTitleTour.enqueue(new Callback<BaseResponse<String>>() {
                @Override
                public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                    if(response.code() == 200){
                        System.out.println("성공~!");
                        String title = edTitle.getText().toString();
//                        albumTitle.postValue(title);

//                        isDelete.postValue(true);
//                        dismiss();
                        // 서버와 통신하여 로그인 성공시
                    } else {
                        // 그밖에 실패시.
                        BasicUtils.showToast(getContext(),"삭제 실패");
                        System.out.println(response.code());
                        System.out.println(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                    System.out.println("실패!!");
                }
            });

        });
    }
}
