package com.wangyongyao.allpowerfulcanvas;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.wangyongyao.allpowerfulcanvas.manager.CanvasEventOperationManager;
import com.wangyongyao.allpowerfulcanvas.views.AllPowerfulCanvasView;
import com.wangyongyao.allpowerfulcanvas.views.TwoFingerSlidingSrcollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.all_powerful_view)
    AllPowerfulCanvasView mAllPowerfulCanvasView;

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
        CanvasEventOperationManager manager = new CanvasEventOperationManager(mAllPowerfulCanvasView,this);
    }

    private void initData() {

    }

    private void initView() {
        ViewGroup.LayoutParams layoutParams = mAllPowerfulCanvasView.getLayoutParams();
        layoutParams.width = (1192*3);
        mAllPowerfulCanvasView.setLayoutParams(layoutParams);
    }
}
