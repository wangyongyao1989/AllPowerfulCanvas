package com.wangyongyao.allpowerfulcanvas.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;

import com.wangyongyao.allpowerfulcanvas.models.PointModle;
import com.wangyongyao.allpowerfulcanvas.models.SegmentsIntersect;
import com.wangyongyao.allpowerfulcanvas.views.AllPowerfulCanvasView;
import com.wangyongyao.allpowerfulcanvas.views.CDrawable;
import com.wangyongyao.allpowerfulcanvas.views.CPath;
import com.wangyongyao.allpowerfulcanvas.views.CirclesWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.utils
 * @describe TODO
 * @date 2018/5/21
 */

public class CanvsWidgetLigatureUtils {

    /**
     * 从直线中获取点
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param span   获取点的跨度值，可用于连线动画的速度计算
     * @return
     */
    public static List<PointModle> getPointFromLine(int startX, int startY,
                                                    int endX, int endY, int span) {
        List<PointModle> list = new ArrayList<>();
        if (startX == endX) {
            int from = Math.min(startY, endY);
            int to = Math.max(startX, endY);
            for (int y = from; y <= to; y++) {
                list.add(new PointModle(startX, y));
            }
        } else {
            double slope = ((double) (endY - startY)) / ((double) (endX - startX));
            int step = (endX > startX) ? span : -span;
            double y = 0;
            if (step > 0) {
                for (int x = startX; x < endX; x += step) {
                    y = ((x - startX) * slope + startY);
                    list.add(new PointModle(x, y));
                }
            } else {
                for (int x = startX; x > endX; x += step) {
                    y = ((x - startX) * slope + startY);
                    list.add(new PointModle(x, y));
                }
            }

        }
        return list;
    }

    /**
     * 连接两点的连接并添加到画布中
     *
     * @param mAllPowerfulCanvasView
     * @param key
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param color
     * @param width
     */
    public static void setLineTo(AllPowerfulCanvasView mAllPowerfulCanvasView, int key, int startX, int startY, int endX, int endY, String color, float width) {
        CPath mCPath;
        Paint mPaint1;
        int mDrawColor = Color.parseColor(color);
        Paint.Style mStyle = Paint.Style.STROKE;
        mCPath = new CPath();
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(mDrawColor);
        mPaint1.setStyle(mStyle);
        mPaint1.setStrokeJoin(Paint.Join.ROUND);
        mPaint1.setStrokeWidth(width);

        mCPath.setPaint(mPaint1);
        mCPath.setXcoords(startX);
        mCPath.setYcoords(startY);
        mCPath.setEndX(endX);
        mCPath.setEndY(endY);
        mAllPowerfulCanvasView.addCanvasDrawableMap(key, mCPath);
    }

    /**
     * 连接两点的连接并添加到画布中
     *
     * @param mAllPowerfulCanvasView
     * @param key
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     */
    public static void setLineTo(AllPowerfulCanvasView mAllPowerfulCanvasView, int key, int startX, int startY, int endX, int endY) {
        CPath mCPath;
        Paint mPaint1;
        int mDrawColor = Color.parseColor("#37c800");
        Paint.Style mStyle = Paint.Style.STROKE;
        float mDrawingSize = 5f;
        mCPath = new CPath();
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(mDrawColor);
        mPaint1.setStyle(mStyle);
        mPaint1.setStrokeJoin(Paint.Join.ROUND);
        mPaint1.setStrokeWidth(mDrawingSize);

        mCPath.setPaint(mPaint1);
        mCPath.setXcoords(startX);
        mCPath.setYcoords(startY);
        mCPath.setEndX(endX);
        mCPath.setEndY(endY);
        mAllPowerfulCanvasView.addCanvasDrawableMap(key, mCPath);
    }

    /**
     * 设置控件前一连线的移动
     *
     * @param drawableMap
     * @param x
     * @param y
     * @param nextWidgetIndex
     */
    public static void setProLineMove(HashMap<Integer, CDrawable> drawableMap, int x, int y, int nextWidgetIndex) {
        CDrawable cd = drawableMap.get(nextWidgetIndex);
        if (null != cd) {
            int xcoords = cd.getXcoords();
            int ycoords = cd.getYcoords();
            if (CanvsUtils.isCirclesWidgetTypeInstance(cd)) {
                int lineIndex = ((CirclesWidget) cd).getNextLineIndex();      //获取保存在控件中的连线值
                CanvsWidgetLigatureUtils.setLineFollowMove(drawableMap, x, y, xcoords, ycoords, lineIndex);
            }
        }
    }

    /**
     * 设置控件后一连线的移动
     *
     * @param drawableMap
     * @param x
     * @param y
     * @param nextWidgetIndex
     */
    public static void setNextLineMove(HashMap<Integer, CDrawable> drawableMap, int x, int y, int nextWidgetIndex) {
        CDrawable cd = drawableMap.get(nextWidgetIndex);
        if (null != cd) {
            int xcoords = cd.getXcoords();
            int ycoords = cd.getYcoords();
            if (CanvsUtils.isCirclesWidgetTypeInstance(cd)) {
                int lineIndex = ((CirclesWidget) cd).getProLineIndex();      //获取保存在控件中的连线值
                setLineFollowMove(drawableMap, xcoords, ycoords, x, y, lineIndex);
            }
        }
    }

    /**
     * 设置坐标使得连线跟随着滑动变化
     *
     * @param x
     * @param y
     * @param xcoords
     * @param ycoords
     * @param lineIndex
     */
    public static void setLineFollowMove(HashMap<Integer, CDrawable> drawableMap, int x, int y, int xcoords, int ycoords, int lineIndex) {
        if (lineIndex < 0) {
            return;
        }
        CPath drawable = (CPath) drawableMap.get(lineIndex);
        if (drawable != null) {
            drawable.setXcoords(xcoords);
            drawable.setYcoords(ycoords);
            drawable.setEndX(x);
            drawable.setEndY(y);
        }
    }



    /**
     * 取消控件内部的关系
     *
     * @param drawableMap
     * @param list
     */
    public static void removeWidgetRelationship(Map<Integer, CDrawable> drawableMap, List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            if (CanvsUtils.isCirclesWidgetTypeInstance(drawableMap.get(list.get(i)))) {
                if (i == 0) {   //如是首帧
                    CirclesWidget drawable = (CirclesWidget) drawableMap.get(list.get(i));
                    drawable.setNextWidget(-1);
                    drawable.setNextLineIndex(-1);
                    continue;
                }
                //加入最后的一个关键帧只清除前连线
                CirclesWidget drawable = (CirclesWidget) drawableMap.get(list.get(i));
                if (i == (list.size()-1)) {
                    drawable.setProWidget(-1);
                    drawable.setProLineIndex(-1);
                    continue;
                }
                if (CanvsUtils.isCirclesWidgetTypeInstance(drawableMap.get(drawable.getProWidget()))) {
                    CirclesWidget drawable1 = (CirclesWidget) drawableMap.get(drawable.getProWidget());
                    drawable1.setNextWidget(-1);
                    drawable1.setNextLineIndex(-1);
                }
                drawable.setProWidget(-1);
                drawable.setProLineIndex(-1);
                drawable.setNextWidget(-1);
                drawable.setNextLineIndex(-1);
            }
        }
        list.clear();
    }

    /**
     * 擦除画布中的连线
     *
     * @param drawableMap
     * @param list
     */
    public static void removeMoveLine(Map<Integer, CDrawable> drawableMap, List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            drawableMap.remove(list.get(i));
        }
    }

    /**
     * 判断控件是否和连线相交
     *
     * @param map
     * @param index
     * @param x
     * @param y
     * @return
     */
    public static boolean isWidgetIntoLine(Map<Integer, CDrawable> map, int index, int x, int y) {
        Iterator iterator = map.keySet().iterator();
        int proLineIndex = -1;
        int nextLineIndex = -1;
        if (CanvsUtils.isCirclesWidgetTypeInstance(map.get(index))) {
            CirclesWidget drawable = (CirclesWidget) map.get(index);
            proLineIndex = drawable.getProLineIndex();
            nextLineIndex = drawable.getNextLineIndex();
        }
        while (iterator.hasNext()) {
            int next = (int) iterator.next();
            if (map.get(next) instanceof CPath) {
                if ((nextLineIndex == next) || (proLineIndex == next)) {
                    continue;
                }
                int xcoords = (map.get(next)).getXcoords();
                int ycoords = (map.get(next)).getYcoords();
                int endX = ((CPath) map.get(next)).getEndX();
                int endY = ((CPath) map.get(next)).getEndY();
                if (pointToLine(xcoords, ycoords, endX, endY, x, y) < 100 * 1.1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断map中连线是否相交
     *
     * @param map
     * @param index
     * @return
     */
    public static boolean isLineToSegmentsIntersect(Map<Integer, CDrawable> map, int index, int x, int y) {
        if ((index > -1) && (CanvsUtils.isCirclesWidgetTypeInstance(map.get(index)))) {
            CirclesWidget actionFrameWidget = (CirclesWidget) map.get(index);
            int proLineIndex = actionFrameWidget.getProLineIndex();
            int nextLineIndex = actionFrameWidget.getNextLineIndex();
            if (proLineIndex > -1) {
                //控件的前一连线值
                if (isWidgetLineSegmentIntersect(map, proLineIndex, x, y, false)) {
                    return true;
                }
            }
            if (nextLineIndex > -1) {
                if (isWidgetLineSegmentIntersect(map, nextLineIndex, x, y, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断控件中的连线是否与画布中其他的连线相交
     */
    @Nullable
    public static Boolean isWidgetLineSegmentIntersect(Map<Integer, CDrawable> map, int nextLineIndex, int x, int y, boolean isNext) {
        if (map.get(nextLineIndex) == null){
            return false;
        }
        CPath cPath = (CPath) map.get(nextLineIndex);
        PointModle pointModle3;
        PointModle pointModle4;
        if (isNext) {
            pointModle3 = new PointModle(x, y);
            pointModle4 = new PointModle(cPath.getEndX(), cPath.getEndY());
        } else {
            pointModle3 = new PointModle(cPath.getXcoords(), cPath.getYcoords());
            pointModle4 = new PointModle(x, y);
        }

        if (isLineSegmentIntersect(map, nextLineIndex, pointModle3, pointModle4)) {
            return true;
        } else {
            if (isLineIntoWidget(map, nextLineIndex, pointModle3, pointModle4, isNext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 控件能不能再连接
     *
     * @param drawableMap
     * @param index
     * @param proWidget
     * @return
     */
    public static boolean isRelation(Map<Integer, CDrawable> drawableMap, int index, int proWidget) {
        if (((CirclesWidget) drawableMap.get(index)).getProWidget() > -1) {     //有前一连接
            return true;
        }
        if (CanvsUtils.isCirclesWidgetTypeInstance(drawableMap.get(proWidget))) {
            ((CirclesWidget) drawableMap.get(proWidget)).setNextWidget(index);//设置长按控件的后连接值
        } else {
            return true;
        }
        return false;
    }

    /**
     * 判断两点连成的直线与画布中存在的连线是否相交
     *
     * @param map
     * @param nextLineIndex
     * @param pointModle3
     * @param pointModle4
     * @return
     */
    public static boolean isLineSegmentIntersect(Map<Integer, CDrawable> map, int nextLineIndex, PointModle pointModle3, PointModle pointModle4) {
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            int next = (int) iterator.next();
            if (map.get(next) instanceof CPath) {
                if (nextLineIndex == next) {
                    continue;
                }
                int xcoords = (map.get(next)).getXcoords();
                int ycoords = (map.get(next)).getYcoords();
                PointModle pointModle1 = new PointModle();
                pointModle1.setX((double) xcoords);
                pointModle1.setY((double) ycoords);
                int endX = ((CPath) map.get(next)).getEndX();
                int endY = ((CPath) map.get(next)).getEndY();
                PointModle pointModle2 = new PointModle();
                pointModle2.setX((double) endX);
                pointModle2.setY((double) endY);
                //两线同处于控件中
                if ((pointModle1.getX() == pointModle3.getX() && pointModle1.getY() == pointModle3.getY())
                        || (pointModle1.getX() == pointModle4.getX() && pointModle1.getY() == pointModle4.getY())
                        || (pointModle2.getX() == pointModle3.getX() && pointModle2.getY() == pointModle3.getY())
                        || (pointModle2.getX() == pointModle4.getX() && pointModle2.getY() == pointModle4.getY())) {
                    continue;
                }
                SegmentsIntersect sege = new SegmentsIntersect(pointModle1, pointModle2, pointModle3, pointModle4);
                if (sege.SegmentsIntersect()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x0
     * @param y0
     * @return
     */
    private static double pointToLine(int x1, int y1, int x2, int y2, int x0, int y0) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// 线段的长度
        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
        if (c <= 0.000001 || b <= 0.000001) {
            space = 0;
            return space;
        }
        if (a <= 0.000001) {
            space = b;
            return space;
        }
        if (c * c >= a * a + b * b) {
            space = b;
            return space;
        }
        if (b * b >= a * a + c * c) {
            space = c;
            return space;
        }
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return space;
    }

    // 计算两点之间的距离
    private static double lineSpace(int x1, int y1, int x2, int y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)
                * (y1 - y2));
        return lineLength;
    }

    /**
     * 拖动的widget（圆形图片）的两条线是否与其他widget（圆形图片）相交
     *
     * @return
     */
    public static boolean isLineIntoWidget(Map<Integer, CDrawable> map, int index, PointModle startpointModle, PointModle endpointModle, boolean isNext) {
        Set<Map.Entry<Integer, CDrawable>> entrySet = map.entrySet();
        Iterator<Map.Entry<Integer, CDrawable>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, CDrawable> entry = iterator.next();
            CDrawable cd = entry.getValue();
            boolean isCirclesWidget = CanvsUtils.isCirclesWidgetTypeInstance(cd);
            if (isCirclesWidget) {
                CirclesWidget kfw = (CirclesWidget) cd;
                int prolineindex = kfw.getProLineIndex();
                int nextlineindex = kfw.getNextLineIndex();
                if (prolineindex == index || nextlineindex == index) {
                    continue;
                }
                if (pointToLine((int) startpointModle.getX(), (int) startpointModle.getY(), (int) endpointModle.getX(), (int) endpointModle.getY(), kfw.getXcoords(), kfw.getYcoords()) < CPath.circleRaidus * 1.1) {
                    return true;
                }
            }
        }
        return false;
    }
}
