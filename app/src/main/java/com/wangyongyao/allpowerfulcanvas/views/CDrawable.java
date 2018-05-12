package com.wangyongyao.allpowerfulcanvas.views;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.views
 * @describe TODO
 * @date 2018/5/12
 */

public interface CDrawable {
    Paint getPaint();                   //获取Paint

    int getXcoords();                   //获取X轴坐标

    int getYcoords();                   //获取Y轴坐标

    void setXcoords(int x);             //设置X轴坐标

    void setYcoords(int y);             //设置Y轴坐标

    void setPaint(Paint p);             //设置画笔

    void draw(Canvas canvas);           //绘制画板

    int getRotation();

    void setRotation(int degree);

    int getDrawableType();              //获取是哪个控件类型
}
