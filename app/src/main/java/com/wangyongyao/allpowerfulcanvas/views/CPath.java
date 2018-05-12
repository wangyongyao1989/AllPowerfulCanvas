package com.wangyongyao.allpowerfulcanvas.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.views
 * @describe TODO
 * @date 2018/5/12
 */

public class CPath implements CDrawable {
    private final String TAG = CPath.class.getSimpleName();
    private int x=0, y=0;
    private Path mPath;
    private int endX=0,endY=0;
    private int DrawableType=1;
    private int key=-1;
    public static float circleRaidus = 0 ;
    private boolean isfullline = true;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    private Paint mPaint;
    private int mRotDegree;
    public CPath() {
        mPath = new Path();
    }

    @Override
    public Paint getPaint() {
        return mPaint;
    }

    @Override
    public int getXcoords() {
        return x;
    }

    @Override
    public int getYcoords() {
        return y;
    }

    @Override
    public void setXcoords(int x) {
        this.x = x;
    }

    @Override
    public void setYcoords(int y) {
        this.y = y;
    }

    @Override
    public void setPaint(Paint p) {
        mPaint = p;
    }

    @Override
    public void draw(Canvas canvas) {
//        Log.i("everb","画直线:");
//        canvas.drawPath(mPath, mPaint);
       /* canvas.drawLine(x,
                y,
                endX,
                endY,
                mPaint);*/
//        if(0 == circleRaidus){
//            Bitmap bm = BitmapFactory.decodeResource(BaseApplication.getInstance().getResources() , R.drawable.action_zhen_00);
//            circleRaidus = KeyFrameWidget.zoomImg(bm).getWidth() / 2 * 2 / 3 ;
//            bm.recycle();
//        }
        float[] newCoords = calcNewXY2NextNewXY(x,y,endX,endY, circleRaidus);
        if(isfullline){
            canvas.drawLine(newCoords[0],
                    newCoords[1],
                    newCoords[2],
                    newCoords[3],
                    mPaint);
        }else{
            drawDottedLine(canvas,mPaint,newCoords);
        }
    }

    @Override
    public int getRotation() {
        return mRotDegree;
    }

    @Override
    public void setRotation(int degree) {
        mRotDegree = degree;
    }

    @Override
    public int getDrawableType() {
        return DrawableType;
    }

    public void lineTo(float eventX, float eventY) {
        mPath.lineTo(eventX, eventY);
    }

    public void moveTo(float eventX, float eventY) {
        mPath.moveTo(eventX, eventY);
    }

    @Override
    public String toString() {
        return "CPath{" +
                "x=" + x +
                ", y=" + y +

                ", endX=" + endX +
                ", endY=" + endY +

                '}';
    }

    private void drawDottedLine(Canvas canvas,Paint mPaint,float[] linedata){
        DashPathEffect pathEffect = new DashPathEffect(new float[] { 5,5 }, 0);
        mPaint.setAntiAlias(true);
        mPaint.setPathEffect(pathEffect);
        mPaint.setColor(Color.parseColor("#4fb9de"));
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(ConvertUtils.dp2px(1));
        int rows = linedata.length / 4;
        for(int i = 0 ; i < rows ; i++){
            Path  path = new Path();
            path.moveTo(linedata[i*4+0],linedata[i*4+1]);
            path.lineTo(linedata[i*4+2],linedata[i*4+3]);
            canvas.drawPath(path, mPaint);
        }
    }
    private float[] calcNewXY2NextNewXY(float startX ,float startY ,float endX,float endY,float circleRaidus){
        float[]  newposition = new float[4];
        double  sinValue = (endX - startX)/Math.sqrt(Math.pow((endX - startX),2) + Math.pow((endY - startY),2));
        double  cosValue  = - (endY - startY)/Math.sqrt(Math.pow((endX - startX),2) + Math.pow((endY - startY),2));
        newposition[0] = (float)(startX + sinValue * circleRaidus);
        newposition[1] = (float)(startY - cosValue * circleRaidus);
        newposition[2] = (float)(endX - sinValue * circleRaidus);
        newposition[3] = (float)(endY + cosValue * circleRaidus);
        return newposition;
    }

    public void setIsfullline(boolean isfullline) {
        this.isfullline = isfullline;
    }

}
