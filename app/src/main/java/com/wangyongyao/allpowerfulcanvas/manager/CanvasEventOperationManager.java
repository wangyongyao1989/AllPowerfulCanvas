package com.wangyongyao.allpowerfulcanvas.manager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.wangyongyao.allpowerfulcanvas.R;
import com.wangyongyao.allpowerfulcanvas.utils.MathUtils;
import com.wangyongyao.allpowerfulcanvas.views.AllPowerfulCanvasView;
import com.wangyongyao.allpowerfulcanvas.views.CDrawable;
import com.wangyongyao.allpowerfulcanvas.views.CirclesWidget;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.manager
 * @describe 管理AllPowerfulCanvasView各种操作
 * @date 2018/5/15
 */

public class CanvasEventOperationManager {

    private AllPowerfulCanvasView mAllPowerfulCanvasView;
    private Context mContext;
    private int mPreX = 0;
    private CirclesWidget mCirclesWidget;
    private int mKey = 0;
    private int mIndex = -1;        // 记录点击的动作帧在集合中位置
    private long mLastTime = 0, mCurrentTime = 0;  // 动作帧被点击的上一次时间戳    动作帧被点击的当前时间戳




    public CanvasEventOperationManager(AllPowerfulCanvasView allPowerfulCanvasView, Context context) {
       this.mAllPowerfulCanvasView = allPowerfulCanvasView;
       this.mContext = context;
       initEvent();
    }

    private void initEvent() {
        /**
         * CircleWidget中的单双击事件的区分
         */
        mAllPowerfulCanvasView.setOnWidgetClickListener(new AllPowerfulCanvasView.onWidgetClickListener() {
            @Override
            public void onWidgetClick(int index, int x, int y) {
                mIndex = index;
                mLastTime = mCurrentTime;
                mCurrentTime = System.currentTimeMillis();
                if (mCurrentTime - mLastTime < 400) {      // 动作帧的双击事件 update 300 to 400
                    mCurrentTime = 0;
                    mLastTime = 0;
                    Toast.makeText(mContext,"控件" +index+"的双击事件",Toast.LENGTH_SHORT).show();
                } else {
                    // 动作帧的单击事件
                    Toast.makeText(mContext,"控件" +index+"的单击事件",Toast.LENGTH_SHORT).show();

                }
            }
        });

        mAllPowerfulCanvasView.setOnWidgetMoveListener(new AllPowerfulCanvasView.onWidgetMoveListener() {
            @Override
            public void onWidgetMove(int index, int x, int y) {

            }
        });

    }

    /**
     * 添加控件CircleWidget在画布中的x,y的位置，控件不重叠
     */
    public void addCirclesWidget() {
        HashMap<Integer, CDrawable> hashMap = mAllPowerfulCanvasView.mDrawableMap;
        Set<Map.Entry<Integer, CDrawable>> entries = hashMap.entrySet();
        for (Map.Entry en : entries)
            if (mPreX < ((CDrawable) en.getValue()).getXcoords()) {
                mPreX = ((CDrawable) en.getValue()).getXcoords();
            }
        int x = MathUtils.getRandomNum(mPreX + MathUtils.dpToPx(mContext,50), mPreX + MathUtils.dpToPx(mContext,50));
        int y = MathUtils.getRandomNum(MathUtils.dpToPx(mContext,50), 1920 - MathUtils.dpToPx(mContext,150));
        Resources resources = mContext.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.action_zhen_03);
        mCirclesWidget = new CirclesWidget(bitmap,x,y);
        mCirclesWidget.setKey(mKey++);
        mAllPowerfulCanvasView.addCanvasDrawableMap(mCirclesWidget.getKey(),mCirclesWidget);
    }


}
