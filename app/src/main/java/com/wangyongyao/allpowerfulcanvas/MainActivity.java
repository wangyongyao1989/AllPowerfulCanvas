package com.wangyongyao.allpowerfulcanvas;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wangyongyao.allpowerfulcanvas.manager.CanvasEventOperationManager;
import com.wangyongyao.allpowerfulcanvas.views.AllPowerfulCanvasView;
import com.wangyongyao.allpowerfulcanvas.views.TwoFingerSlidingSrcollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.all_powerful_view)
    AllPowerfulCanvasView mAllPowerfulCanvasView;

    @BindView(R.id.two_finger_sliding_scrollview)
    TwoFingerSlidingSrcollView mTwoFingerSlidingScrollview;

    @BindView(R.id.iv_motor_add)
    ImageView mIvMotorAdd;

    @BindView(R.id.iv_motor_preview)
    ImageView mIvMotorPreview;

    @BindView(R.id.iv_motor_apply)
    ImageView mIvMotorApply;

    private CanvasEventOperationManager mOperationManager;

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

        mOperationManager = new CanvasEventOperationManager(mAllPowerfulCanvasView, this);

    }

    private void initData() {

    }

    private void initView() {
        ViewGroup.LayoutParams layoutParams = mAllPowerfulCanvasView.getLayoutParams();
        layoutParams.width = (1192 * 3);
        mAllPowerfulCanvasView.setLayoutParams(layoutParams);
    }

    @OnClick({R.id.iv_motor_add, R.id.iv_motor_preview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_motor_add: {
                mOperationManager.addCirclesWidget();
            }
                break;
            case R.id.iv_motor_preview:
                break;
        }
    }

}
