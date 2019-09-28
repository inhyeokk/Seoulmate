package com.soksok.seoulmate.view.main.adapter;

import android.view.View;

import com.soksok.seoulmate.http.model.Tour;

public interface MyTripItemListener {

    void onLayoutClick(View v, Tour tour);

    void onMenuClick(View v, int position);
}
