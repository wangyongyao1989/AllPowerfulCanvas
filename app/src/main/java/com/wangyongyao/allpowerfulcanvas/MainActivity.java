package com.wangyongyao.allpowerfulcanvas;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.wangyongyao.allpowerfulcanvas.views.AllPowerfulView;
import com.wangyongyao.allpowerfulcanvas.views.TwoFingerSlidingSrcollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.all_powerful_view)
    AllPowerfulView mAllPowerfulView;

    @BindView(R.id.two_finger_sliding_scrollview)
    TwoFingerSlidingSrcollView mTwoFingerSlidingScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {

    }

    private void initData() {

    }

    private void initView() {
    }
}
