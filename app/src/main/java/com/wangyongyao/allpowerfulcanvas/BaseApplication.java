package com.wangyongyao.allpowerfulcanvas;

import android.app.Application;
import android.content.Context;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas
 * @describe TODO
 * @date 2018/5/15
 */

public class BaseApplication extends Application {
    private static Context sInstance;

    public static Context getInstance() {
        return sInstance;
    }
}
