package com.wangyongyao.allpowerfulcanvas.manager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public CanvasEventOperationManager(AllPowerfulCanvasView allPowerfulCanvasView, Context context) {
       this.mAllPowerfulCanvasView = allPowerfulCanvasView;
       this.mContext = context;
    }

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
        mCirclesWidget.setKey(++mKey);
        mAllPowerfulCanvasView.addCanvasDrawableMap(mCirclesWidget.getKey(),mCirclesWidget);
    }


}
