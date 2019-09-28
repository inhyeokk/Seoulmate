package com.soksok.seoulmate.view.chat.domain;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.soksok.seoulmate.base.BaseViewModel;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.view.chat.entity.ChatItem;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatViewModel extends BaseViewModel {

    public MutableLiveData<ChatItem> chatItem = new MutableLiveData<>();

    public void getImageCachePath(ChatItem item, Uri uri) {

        compositeDisposable.add(
                BasicUtils.getImageCachePathFromURI(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(path -> {
                    Log.d("imagePath", path);
                    item.setImagePath(path);
                    chatItem.postValue(item);
                })
        );
    }
}
