package com.wangyongyao.allpowerfulcanvas.manager;

import android.content.Context;

import com.wangyongyao.allpowerfulcanvas.views.AllPowerfulCanvasView;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.manager
 * @describe 管理AllPowerfulCanvasView各种操作
 * @date 2018/5/15
 */

public class CanvasEventOperationManager {

    private AllPowerfulCanvasView mAllPowerfulCanvasView;
    private Context mContext;

    public CanvasEventOperationManager(AllPowerfulCanvasView allPowerfulCanvasView, Context context) {
       this.mAllPowerfulCanvasView = allPowerfulCanvasView;
       this.mContext = context;
    }


}
