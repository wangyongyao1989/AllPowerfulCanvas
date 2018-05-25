package com.wangyongyao.allpowerfulcanvas.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Set;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.views
 * @describe TODO
 * @date 2018/5/15
 */

public class AllPowerfulCanvasView extends BaseCanvsView {

    private static final int RADIUS = 100;                  //半径的值
    private boolean isAcceptClickEvent = true;                //是否接受点击事件
    private boolean isDragWidgetMode = true;                 //控件在画布中是否被拖拽
    private long mDownTime;                                 //按下时的时间
    private long mUpTime;
    private long mMoveTime;
    private float DragDownX;
    private float DragDownY;
    private int mDown2Widget;

    private float moveX;
    private float moveY;
    private float moveX1;
    private float moveY1;

    private long currentMS;

    private int mUp2Widget = -1;

    public AllPowerfulCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 增加画布里面的控件或其他实现了CDrawable接口的类
     */
    public boolean addCanvasDrawableMap(int key, CDrawable cDrawable) {
        super.mDrawableMap.put(key, cDrawable);
        invalidate();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return dragModle(event);
    }

    /**
     * 拖拽的TouchEvent
     *
     * @param event
     * @return
     */
    private boolean dragModle(MotionEvent event) {
        if (!isAcceptClickEvent) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mDownTime = System.currentTimeMillis();
                DragDownX = event.getX();//float DragDownX
                DragDownY = event.getY();//float DragDownY

                if (mOnOutOfWidgetDownListener != null) {
                    mOnOutOfWidgetDownListener.onOutOfWidgetDown((int) DragDownX, (int) DragDownY);
                }
                //判断点击的坐标范围是否在控件上
                mDown2Widget = getDown2Widget(DragDownX, DragDownY);
                //     Log.d(TAG,"dragModle ACTION_DOWN mDown2Widget = " + mDown2Widget);
                if (mDown2Widget > -1) {
                    if (mOnWidgetDownListener != null) {
                        mOnWidgetDownListener.onWidgetDown(mDown2Widget, (int) DragDownX, (int) DragDownY);
                    }
                }
                moveX = 0;
                moveY = 0;
                moveX1 = 0;
                moveY1 = 0;
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                float ex = event.getX();
                float ey = event.getY();
                moveX += Math.abs(ex - DragDownX);//X轴距离
                moveY += Math.abs(ey - DragDownY);//y轴距离
                moveX1 = ex;
                moveY1 = ey;
                // Log.d(TAG,"dragModle ACTION_MOVE   mDown2Widget = " + mDown2Widget + " moveX = " + moveX + "  moveY = " + moveY);
                mMoveTime = System.currentTimeMillis();
                long DValueTime = mMoveTime - mDownTime;
                //Log.d(TAG," DValueTime  =(mMoveTime - mDownTime) = " +(DValueTime) );
                if (moveX <= 20 && moveY <= 20 && DValueTime > 150) {
                    if (mOnWidgetLongPressListener != null) {
                        mOnWidgetLongPressListener.onWidgetLongPress(mDown2Widget, (int) moveX1, (int) moveY1);
                    }
                    return true;

                } else {
                    if (mDown2Widget > -1) {
                        if (mOnWidgetMoveListener != null) {
                            mOnWidgetMoveListener.onWidgetMove(mDown2Widget, (int) moveX1, (int) moveY1);
                            if (isDragWidgetMode) {
                                if (mDrawableMap.get(mDown2Widget) instanceof CirclesWidget) {
//                                    Log.i("everb", "移动的值：" + mDown2Widget);
                                    mDrawableMap.get(mDown2Widget).setXcoords((int) moveX1 -
                                            ((CirclesWidget) mDrawableMap.get(mDown2Widget)).getWidth() / 2);

                                    mDrawableMap.get(mDown2Widget).setYcoords((int) moveY1 -
                                            ((CirclesWidget) mDrawableMap.get(mDown2Widget)).getHeight() / 2);
                                }
                                mOnWidgetMoveListener.onWidgetMove(mDown2Widget, (int) moveX1, (int) moveY1);
                                //Log.e(TAG,"............move.........");
                                invalidate();
                            }
                        }
                    } else {
                        if (mOnOutOfWidgetMoveListener != null) {
                            mOnOutOfWidgetMoveListener.onOutOfWidgetMove((int) moveX1, (int) moveY1);
                        }
                    }
                }
                DragDownX = ex;
                DragDownY = ey;
                //Log.e(TAG,"ACTION_MOVE DragDownX = " + ex + " DragDownY = " + ey);
            }
            break;
            case MotionEvent.ACTION_UP: {
                if (mOnOutOfWidgetUpListener != null) {
                    mOnOutOfWidgetUpListener.onOutOfWidgetUp((int) moveX1, (int) moveY1);
                }
                long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                mUpTime = System.currentTimeMillis();
                long DValueTime = mUpTime - mDownTime;
                //  Log.d(TAG,"dragModle ACTION_UP mDown2Widget = " + mDown2Widget);
                if (mDown2Widget > -1) {
                    //判断是否为拖动事件
                    if (!(moveTime > 1000 && (moveX > 100 || moveY > 100))) {
                        if (DValueTime < 300) { // update 200 to 300    2017.10.24
                            if (mOnWidgetClickListener != null) {
                                mOnWidgetClickListener.onWidgetClick(mDown2Widget, (int) moveX1, (int) moveY1);
                            }
                        }
                    }
                } else {
                    if (!(moveTime > 1000 && (moveX > 100 || moveY > 100))) {
                        if (DValueTime < 200) {
                            if (mOnOutOfWidgetClickListener != null) {
                                mOnOutOfWidgetClickListener.onWidgetClick();
                            }
                        }
                    }
                }

                //判断抬起时划线的为位置是否在控件内
                mUp2Widget = getDown2Widget(moveX1, moveY1);
                if (mDown2Widget > -1) {
                    if (mOnWidgetUpListener != null) {
                        mOnWidgetUpListener.onWidgetUp(mUp2Widget, (int) moveX1, (int) moveY1);
                    }
                }
            }
            break;
        }
        return true;
    }


    /**
     * 设置canvas是否接收点击事件
     *
     * @param acceptClickEvent
     */
    public void setAcceptClickEvent(boolean acceptClickEvent) {
        isAcceptClickEvent = acceptClickEvent;
    }

    /**
     * 设置Canvas是否接收拖拽
     *
     * @param dragMode
     */
    public void setDragMode(boolean dragMode) {
        isDragWidgetMode = dragMode;
    }

    public boolean isDragWidgetMode() {
        return isDragWidgetMode;
    }

    /**
     * 手势抬起时不在控件范围的监听
     *
     * @param mOnOutOfWidgetUpListener
     */
    public void setOnOutOfWidgetUpListener(onOutOfWidgetUpListener mOnOutOfWidgetUpListener) {
        this.mOnOutOfWidgetUpListener = mOnOutOfWidgetUpListener;
    }

    /**
     * 手势移动时在控件之外的监听
     *
     * @param mOnOutOfWidgetMoveListener
     */
    public void setOnOutOfWidgetMoveListener(onOutOfWidgetMoveListener mOnOutOfWidgetMoveListener) {
        this.mOnOutOfWidgetMoveListener = mOnOutOfWidgetMoveListener;
    }

    /**
     * 手势按下时在控件之外的监听
     *
     * @param mOnOutOfWidgetDownListener
     */
    public void setOnOutOfWidgetDownListener(onOutOfWidgetDownListener mOnOutOfWidgetDownListener) {
        this.mOnOutOfWidgetDownListener = mOnOutOfWidgetDownListener;
    }

    /**
     * 手势按下时在控件之内的监听
     *
     * @param mOnWidgetDownListener
     */
    public void setOnWidgetDownListener(onWidgetDownListener mOnWidgetDownListener) {
        this.mOnWidgetDownListener = mOnWidgetDownListener;
    }

    /**
     * 手势抬起时在控件之内的监听
     *
     * @param mOnWidgetUpListener
     */
    public void setOnWidgetUpListener(onWidgetUpListener mOnWidgetUpListener) {
        this.mOnWidgetUpListener = mOnWidgetUpListener;
    }

    /**
     * 手势移动时在控件之内的监听
     *
     * @param moveListener
     */
    public void setOnWidgetMoveListener(onWidgetMoveListener moveListener) {
        this.mOnWidgetMoveListener = moveListener;
    }

    /**
     * 长按控件的监听
     *
     * @param mOnWidgetLongPressListener
     */
    public void setOnWidgetLongPressListener(onWidgetLongPressListener mOnWidgetLongPressListener) {
        this.mOnWidgetLongPressListener = mOnWidgetLongPressListener;
    }

    /**
     * 点击控件的监听
     *
     * @param mOnWidgetClickListener
     */
    public void setOnWidgetClickListener(onWidgetClickListener mOnWidgetClickListener) {
        this.mOnWidgetClickListener = mOnWidgetClickListener;
    }

    /**
     * 点击控件之外的监听
     *
     * @param mOnOutOfWidgetClickListener
     */
    public void setOnOutOfWidgetClickListener(onOutOfWidgetClickListener mOnOutOfWidgetClickListener) {
        this.mOnOutOfWidgetClickListener = mOnOutOfWidgetClickListener;
    }


    /**
     * 传入坐标判断坐标是落在mDrawableMap哪个控件之上，如是-1则不在mDrawableMap中控件之内
     *
     * @param x
     * @param y
     * @return
     */
    private int getDown2Widget(float x, float y) {
        int xcoords = 0;
        int ycoords = 0;
        Set<Integer> integers = mDrawableMap.keySet();
        for (Integer ketSet : integers) {
            if (mDrawableMap.get(ketSet).getDrawableType() == 2) {
                xcoords = mDrawableMap.get(ketSet).getXcoords();
                ycoords = mDrawableMap.get(ketSet).getYcoords();
                double abs = Math.sqrt((x - xcoords) * (x - xcoords) + (y - ycoords) * (y - ycoords));
                //点落在控件内
                //   Log.d(TAG," x = " + x +" xcoords  = " +xcoords + " (x - xcoords) = " +(x-xcoords) +" y = " + y + " ycoords = " + ycoords +" (y - ycoords) = " + (y-ycoords) );
                //  Log.d(TAG,"getDown2Widget keySet = " + ketSet +" abs = " + abs);
                if (abs < RADIUS) {
                    return ketSet;
                }
            }
        }
        return -1;
    }

    public onOutOfWidgetUpListener mOnOutOfWidgetUpListener;

    public interface onOutOfWidgetUpListener {
        void onOutOfWidgetUp(int x, int y);
    }

    public onOutOfWidgetMoveListener mOnOutOfWidgetMoveListener;

    public interface onOutOfWidgetMoveListener {
        void onOutOfWidgetMove(int x, int y);
    }

    public onOutOfWidgetDownListener mOnOutOfWidgetDownListener;

    public interface onOutOfWidgetDownListener {
        void onOutOfWidgetDown(int x, int y);
    }

    public onWidgetDownListener mOnWidgetDownListener;

    public interface onWidgetDownListener {
        void onWidgetDown(int index, int x, int y);
    }

    public onWidgetUpListener mOnWidgetUpListener;

    public interface onWidgetUpListener {
        void onWidgetUp(int index, int x, int y);
    }

    public onWidgetMoveListener mOnWidgetMoveListener;

    public interface onWidgetMoveListener {
        void onWidgetMove(int index, int x, int y);
    }

    public onWidgetLongPressListener mOnWidgetLongPressListener;

    public interface onWidgetLongPressListener {
        void onWidgetLongPress(int index, int x, int y);
    }

    public onWidgetClickListener mOnWidgetClickListener;

    public interface onWidgetClickListener {
        void onWidgetClick(int index, int x, int y);
    }

    public onOutOfWidgetClickListener mOnOutOfWidgetClickListener;

    public interface onOutOfWidgetClickListener {
        void onWidgetClick();
    }

}
