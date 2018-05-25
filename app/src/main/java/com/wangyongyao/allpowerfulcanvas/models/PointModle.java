package com.wangyongyao.allpowerfulcanvas.models;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.models
 * @describe TODO
 * @date 2018/5/21
 */

public class PointModle {
    private double x;
    private double y;

    public PointModle() {
        super();
    }
    public PointModle(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
}
