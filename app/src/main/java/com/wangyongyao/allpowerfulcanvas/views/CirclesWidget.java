package com.wangyongyao.allpowerfulcanvas.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.wangyongyao.allpowerfulcanvas.BaseApplication;
import com.wangyongyao.allpowerfulcanvas.R;

import java.util.Arrays;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.views
 * @describe TODO
 * @date 2018/5/15
 */

public class CirclesWidget implements CDrawable {


    private int coordinateX, coordinateY, height, width;
    private Bitmap mBitmap;
    private Paint mPaint;

    private String fileName;

    private Integer id;


    public int actionType;
    private int key=-1;

    private int mHeight;                //图片绘制的高
    private int mWidth;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    private int mProWidget = -1;                           //控件前一个连接控件的值
    private int mProLineIndex = -1;                           //连接控件前线段的值
    private int mNextLineIndex = -1 ;                          //连接控件后线段的值
    private int mNextWidget = -1;                          //控件后一个连接控件的值

    private int DrawableType=2;

    public CirclesWidget(Bitmap src, int x, int y) {
        this(src, x, y, null);
    }

    public CirclesWidget(Bitmap src, int x, int y, Paint p) {
        mBitmap = src;
        setXcoords(x);
        setYcoords(y);
        setPaint(p);
    }


    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap=bitmap;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId( int value){
        this.id = value;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public int getNextLineIndex() {
        return mNextLineIndex;
    }

    public void setNextLineIndex(int nextLineIndex) {
        mNextLineIndex = nextLineIndex;
    }

    public int getProLineIndex() {
        return mProLineIndex;
    }

    public void setProLineIndex(int proLineIndex) {
        mProLineIndex = proLineIndex;
    }

    public int getProWidget() {
        return mProWidget;
    }

    public void setProWidget(int proWidget) {
        this.mProWidget = proWidget;
    }

    public int getNextWidget() {
        return mNextWidget;
    }

    public void setNextWidget(int nextWidget) {
        this.mNextWidget = nextWidget;
    }


    @Override
    public Paint getPaint() {
        return null;
    }

    @Override
    public int getXcoords() {
        return coordinateX;
    }

    @Override
    public int getYcoords() {
        return coordinateY;
    }

    @Override
    public void setXcoords(int x) {
        this.coordinateX = x;
    }

    @Override
    public void setYcoords(int y) {
        this.coordinateY = y;
    }

    @Override
    public void setPaint(Paint p) {
        this.mPaint = p;
    }


    @Override
    public int getRotation() {
        return 0;
    }

    @Override
    public void setRotation(int degree) {

    }

    @Override
    public int getDrawableType() {
        return DrawableType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getActionType() {
        return actionType;
    }


    @Override
    public void draw(Canvas canvas) {
        if (mBitmap==null){
            mBitmap= BitmapFactory.decodeResource(BaseApplication.getInstance().getResources() , R.drawable.action_zhen_03);
        }
        Bitmap bitmap = zoomImg(mBitmap);
        canvas.drawBitmap(bitmap, coordinateX- getSize(mBitmap)/2, coordinateY-getSize(mBitmap)/2, mPaint);
    }

    /**
     *  处理图片
     */
    public static Bitmap zoomImg(Bitmap bm){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();

//        // 计算缩放比例
        float scaleWidth =width/width;
        float scaleHeight = height/height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    private float getSize(Bitmap bm){
        return bm.getHeight();
    }

    public int getRadius(Bitmap bm){
        return bm.getWidth()/2;
    }
}
