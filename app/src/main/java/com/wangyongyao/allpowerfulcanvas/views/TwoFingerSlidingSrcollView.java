package com.wangyongyao.allpowerfulcanvas.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.views
 * @describe TODO
 * @date 2018/5/15
 */

public class TwoFingerSlidingSrcollView extends HorizontalScrollView {
    private boolean isDragCanvsMode;
    private boolean isDoubleDrag = false;
    private float mStartDownX;
    private float mMoveDragCanvsX;
    private boolean isSetDrag = true;       //TwoFingerSlidingSrcollView是否可拖拽

    public TwoFingerSlidingSrcollView(Context context) {
        super(context);
    }

    public TwoFingerSlidingSrcollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoFingerSlidingSrcollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSetDrag(boolean setDrag) {
        isSetDrag = setDrag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //多点拖动的操作
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
//                Log.i("everb","按下去："+mStartDownX);
            }
            break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                isDoubleDrag = true;
            }
            break;
            case MotionEvent.ACTION_MOVE: {
//                Log.i("everb", "canvas scrollview onTouchEvent：" + event.getX() + " " + event.getY() + " event.getAction(): " + event.getAction()+ " isDoubleDrag:"+isDoubleDrag);
                mMoveDragCanvsX = event.getX();
                Log.i("everb", "move的值：" + mMoveDragCanvsX);
//                    Log.i("everb","移动的值："+mMoveDragCanvsX);
                float canvsMoveX = mMoveDragCanvsX - mStartDownX;
                Log.i("everb", "移动的值：" + canvsMoveX);
                this.scrollBy(-(int) canvsMoveX, 0);
            }
            break;
            case MotionEvent.ACTION_CANCEL: {
                isDoubleDrag = false;
//                Log.i("everb", "canvas scrollview onTouchEvent：ACTION_CANCEL");
            }
            break;
            case MotionEvent.ACTION_UP: {
                if (mOnActionPointerDownListener != null) {
                    mOnActionPointerDownListener.isOnActionPointerDownListener(false);
                }
            }
            break;
        }
        mStartDownX = mMoveDragCanvsX;
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isSetDrag) {
            return false;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mStartDownX = event.getX();
                isDragCanvsMode = false;
            }
            break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                isDragCanvsMode = true;
                if (mOnActionPointerDownListener != null) {
                    mOnActionPointerDownListener.isOnActionPointerDownListener(true);
                }
            }
            break;
        }
//        Log.i("everb","canvas scrollview onInterceptTouchEvent："+event.getX()+" "+event.getY() + " event.getAction()"+event.getAction());

        if (isDragCanvsMode == false) {
            return false;
        }
//        Log.i("everb","canvas scrollview onInterceptTouchEvent："+event.getX()+" "+event.getY() + " event.getAction()"+event.getAction());
        return true;
    }


    /**
     * 双指滑动的监听
     *
     * @param mOnActionPointerDownListener
     */
    public void setOnActionPointerDownListener(onActionPointerDownListener mOnActionPointerDownListener) {
        this.mOnActionPointerDownListener = mOnActionPointerDownListener;
    }

    /**
     * ScrollView是否有滚动的监听
     * @param on
     */
    public void setOnScrollChangeListener(onScrollChangeListener on) {
        this.mOnScrollChangeListener = on;
    }

    @Override
    public void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScrollChange(scrollX, scrollY, oldScrollX, oldScrollY);
        }
    }

    private onScrollChangeListener mOnScrollChangeListener;

    public interface onScrollChangeListener {
        void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }


    public onActionPointerDownListener mOnActionPointerDownListener;

    public interface onActionPointerDownListener {
        void isOnActionPointerDownListener(boolean actionPointerDownListener);
    }

}
