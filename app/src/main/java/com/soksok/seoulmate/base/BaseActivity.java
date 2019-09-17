package com.soksok.seoulmate.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

abstract public class BaseActivity extends AppCompatActivity {

    private int layoutRes;
    private boolean isUseDataBinding;

    public int getLayoutRes() {
        return layoutRes;
    }

    public void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    public boolean isUseDataBinding() {
        return isUseDataBinding;
    }

    public void setUseDataBinding(boolean useDataBinding) {
        isUseDataBinding = useDataBinding;
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (layoutRes != 0) {
            if (isUseDataBinding) {
                setContentView(layoutRes);
            } else {
                onDataBinding();
            }
        }
        setupViews();
        subscribeUI();
    }

    abstract protected void onDataBinding();

    abstract protected void setupViews();
    abstract protected void subscribeUI();
}
