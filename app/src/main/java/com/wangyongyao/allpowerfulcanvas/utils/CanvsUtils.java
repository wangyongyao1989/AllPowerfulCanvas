package com.wangyongyao.allpowerfulcanvas.utils;

import android.util.Log;

import com.wangyongyao.allpowerfulcanvas.views.CDrawable;
import com.wangyongyao.allpowerfulcanvas.views.CirclesWidget;

import java.util.Map;
import java.util.Set;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.utils
 * @describe TODO
 * @date 2018/5/21
 */

public class CanvsUtils {

    /**
     * 保证类型强转的安全
     *
     * @param o1
     * @return
     */
    public static boolean isCirclesWidgetTypeInstance(CDrawable o1) {
        return (o1 instanceof CirclesWidget) ? true : false;
    }

    /**
     * 判断x、y位置是否在控件内
     * @param x
     * @param y
     * @param drawableMap
     * @return
     */
    public  int getDown2Widget(float x, float y , Map<Integer,CDrawable> drawableMap, int radius) {
        int xcoords=0;
        int ycoords=0;
        Set<Integer> integers = drawableMap.keySet();
        for (Integer ketSet:integers) {
            if (drawableMap.get(ketSet).getDrawableType()==2){
                if (drawableMap.get(ketSet) instanceof CirclesWidget){
                    //加入是图片的话中心的偏移
                    xcoords = drawableMap.get(ketSet).getXcoords()+((CirclesWidget)drawableMap.get(ketSet)).getWidth()/2;
                    ycoords = drawableMap.get(ketSet).getYcoords()+((CirclesWidget)drawableMap.get(ketSet)).getHeight()/2;
                }else {
                    xcoords= drawableMap.get(ketSet).getXcoords();
                    ycoords = drawableMap.get(ketSet).getYcoords();
                }
                double abs = Math.sqrt((x - xcoords) * (x - xcoords) + (y  - ycoords) * (y  - ycoords));
                //点落在控件内
                Log.i("everb","关键帧："+drawableMap.get(0));
                if (abs < radius*1.4) {
                    return ketSet;
                }
            }
        }
        return -1;
    }
}
