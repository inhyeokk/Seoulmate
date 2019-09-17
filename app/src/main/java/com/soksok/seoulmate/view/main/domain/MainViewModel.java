package com.soksok.seoulmate.view.main.domain;

import com.soksok.seoulmate.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    private MainRepository repo;

    public MainViewModel(MainRepository repo) {
        this.repo = repo;
    }
}
