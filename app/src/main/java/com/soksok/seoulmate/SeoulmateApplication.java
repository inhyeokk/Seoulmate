package com.soksok.seoulmate;

import android.app.Application;

import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.common.BindUtils;

public class SeoulmateApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initUtils();
    }

    private void initUtils() {

        BasicUtils.init(this);
        BindUtils.init(this);
    }
}
