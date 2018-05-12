package com.wangyongyao.allpowerfulcanvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Set;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.views
 * @describe TODO
 * @date 2018/5/12
 */

public class BaseCanvsView extends View {
    public HashMap<Integer,CDrawable> mDrawableMap=new HashMap<>();

//    public ArrayList<CDrawable> mDrawableList = new ArrayList<>();

    private int mColor = Color.BLACK;

    // Canvas interaction modes
    private int mInteractionMode = LOCKED_MODE;

    // background color of the library
    private int mBackgroundColor = Color.WHITE;
    // default style for the library
    private Paint.Style mStyle = Paint.Style.STROKE;

    // default stroke size for the library    默认点击的尺寸
    private float mSize = 5f;

    // flag indicating whether or not the background needs to be redrawn
    private boolean mRedrawBackground;

    // background mode for the library, default to blank
    private int mBackgroundMode = BACKGROUND_STYLE_BLANK;

    // Default Notebook left line color
    public static final int NOTEBOOK_LEFT_LINE_COLOR = Color.RED;

    // Flag indicating that we are waiting for a location for the text
    private boolean mTextExpectTouch;

    // Vars to decrease dirty area and increase performance
    private float lastTouchX, lastTouchY;
    private final RectF dirtyRect = new RectF();

    Paint currentPaint;

    /*********************************************************************************************/
    /************************************     FLAGS    *******************************************/
    /*********************************************************************************************/
    // Default Background Styles         背景颜色
    public static final int BACKGROUND_STYLE_BLANK = 0;
    public static final int BACKGROUND_STYLE_NOTEBOOK_PAPER = 1;
    public static final int BACKGROUND_STYLE_GRAPH_PAPER = 2;

    // Interactive Modes
    public static final int DRAW_MODE = 0;
    public static final int SELECT_MODE = 1; // TODO Support Object Selection.
    public static final int ROTATE_MODE = 2; // TODO Support Object ROtation.
    public static final int LOCKED_MODE = 3;

    /*********************************************************************************************/
    /**********************************     CONSTANTS    *****************************************/
    /*********************************************************************************************/
    public static final int NOTEBOOK_LEFT_LINE_PADDING = 120;

    /*********************************************************************************************/
    /************************************     TO-DOs    ******************************************/
    /*********************************************************************************************/
    private float mZoomLevel = 1.0f; //TODO Support Zoom                要去做支持放大缩小的功能
    private float mHorizontalOffset = 1, mVerticalOffset = 1; // TODO Support Offset and Viewport  支持可以偏移的功能
    public int mAutoscrollDistance = 100; // TODO Support Autoscroll




    public BaseCanvsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setBackgroundColor(mBackgroundColor);
        mTextExpectTouch = false;
    }


    /**
     * Called when there is the canvas is being re-drawn.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // check if background needs to be redrawn
//        mBackgroundMode=BACKGROUND_STYLE_NOTEBOOK_PAPER;
        drawBackground(canvas, mBackgroundMode);

        // go through each item in the list and draw it

        Set<Integer> integers = mDrawableMap.keySet();
        for (Integer ketSet:integers) {
            mDrawableMap.get(ketSet).draw(canvas);
        }

    }


    /*********************************************************************************************/
    /*******************************     Handling User Touch    **********************************/
    /*********************************************************************************************/

    /**
     * Handles user touch event
     *
     * @param event the user's motion event
     * @return true, the event is consumed.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // delegate action to the correct method
        if (getInteractionMode() == DRAW_MODE)                  //绘画的模式
            return true;
        else if (getInteractionMode() == SELECT_MODE)           //选择的模式
            return onTouchSelectMode(event);
        else if (getInteractionMode() == ROTATE_MODE)
            return onTouchRotateMode(event);
            // if none of the above are selected, delegate to locked mode
        else
            return onTouchLockedMode(event);
    }

    /**
     * Handles touch event if the mode is set to locked
     * @param event the event to handle
     * @return false, shouldn't do anything with it for now
     */
    private boolean onTouchLockedMode(MotionEvent event) {
        // return false since we don't want to do anything so far
        return true;
    }

    /**
     * Handles the touch input if the mode is set to rotate
     * @param event the touch event
     * @return the result of the action
     */
    private boolean onTouchRotateMode(MotionEvent event) {
        return false;
    }




    /**
     * Handles the touch input if the mode is set to select
     * @param event the touch event
     */
    public boolean onTouchSelectMode(MotionEvent event) {
        // TODO Implement Method
        return true;
    }


    /*******************************************
     * Drawing Events
     ******************************************/
    /**
     * Draw the background on the canvas
     * @param canvas the canvas to draw on
     * @param backgroundMode one of BACKGROUND_STYLE_GRAPH_PAPER, BACKGROUND_STYLE_NOTEBOOK_PAPER, BACKGROUND_STYLE_BLANK
     */
    public void drawBackground(Canvas canvas, int backgroundMode) {
        canvas.drawColor(mBackgroundColor);
        if(backgroundMode != BACKGROUND_STYLE_BLANK) {
            Paint linePaint = new Paint();
            linePaint.setColor(Color.argb(50, 0, 0, 0));
            linePaint.setStyle(mStyle);
            linePaint.setStrokeJoin(Paint.Join.ROUND);
            linePaint.setStrokeWidth(mSize - 2f);
            switch (backgroundMode) {
                case BACKGROUND_STYLE_GRAPH_PAPER:
                    drawGraphPaperBackground(canvas, linePaint);
                    break;
                case BACKGROUND_STYLE_NOTEBOOK_PAPER:
                    drawNotebookPaperBackground(canvas, linePaint);
                default:
                    break;
            }
        }
        mRedrawBackground = false;
    }

    /**
     * Draws a graph paper background on the view
     * @param canvas the canvas to draw on
     * @param paint the paint to use
     */
    private void drawGraphPaperBackground(Canvas canvas, Paint paint) {
        int i = 0;
        boolean doneH = false, doneV = false;

        // while we still need to draw either H or V
        while (!(doneH && doneV)) {

            // check if there is more H lines to draw
            if (i < canvas.getHeight())
                canvas.drawLine(0, i, canvas.getWidth(), i, paint);
            else
                doneH = true;

            // check if there is more V lines to draw
            if (i < canvas.getWidth())
                canvas.drawLine(i, 0, i, canvas.getHeight(), paint);
            else
                doneV = true;

            // declare as done
            i += 75;
        }
    }

    /**
     * Draws a notebook paper background on the view
     * @param canvas the canvas to draw on
     * @param paint the paint to use
     */
    private void drawNotebookPaperBackground(Canvas canvas, Paint paint) {
        int i = 0;
        boolean doneV = false;
        // draw horizental lines
        while (!(doneV)) {
            if (i < canvas.getHeight())
                canvas.drawLine(0, i, canvas.getWidth(), i, paint);
            else
                doneV = true;
            i += 75;
        }
        // change line color
        paint.setColor(NOTEBOOK_LEFT_LINE_COLOR);
        // draw side line
        canvas.drawLine(NOTEBOOK_LEFT_LINE_PADDING, 0,
                NOTEBOOK_LEFT_LINE_PADDING, canvas.getHeight(), paint);


    }


    /**
     * Clean the canvas, remove everything drawn on the canvas.
     */
    public void cleanPage() {
        // remove everything from the list
        while(!(mDrawableMap.isEmpty())){
            mDrawableMap.remove(0);
        }
        // request to redraw the canvas
        invalidate();
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getBackgroundMode() {
        return mBackgroundMode;
    }

    public void setBackgroundMode(int mBackgroundMode) {
        this.mBackgroundMode = mBackgroundMode;
        invalidate();
    }

    public void setBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
    }

    public Paint.Style getStyle() {
        return mStyle;
    }

    public void setStyle(Paint.Style mStyle) {
        this.mStyle = mStyle;
    }

    public float getSize() {
        return mSize;
    }

    public void setSize(float mSize) {
        this.mSize = mSize;
    }


    public int getInteractionMode() {
        return mInteractionMode;
    }

    public void setInteractionMode(int interactionMode) {

        // if the value passed is not any of the flags, set the library to locked mode
        if (interactionMode > LOCKED_MODE)
            interactionMode = LOCKED_MODE;
        else if (interactionMode < DRAW_MODE)
            interactionMode = LOCKED_MODE;

        this.mInteractionMode = interactionMode;
    }
}
