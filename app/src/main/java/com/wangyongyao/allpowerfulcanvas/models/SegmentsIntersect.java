package com.wangyongyao.allpowerfulcanvas.models;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.models
 * @describe 两线段是否是否相交的判断
 * @date 2018/5/21
 */

public class SegmentsIntersect {
    private PointModle p1;
    private PointModle p2;
    private PointModle p3;
    private PointModle p4;
    public SegmentsIntersect(PointModle p1, PointModle p2, PointModle p3, PointModle p4) {
        super();
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }
    public double det(PointModle pi, PointModle pj) { // 叉积
        return pi.getX() * pj.getY() - pj.getX() * pi.getY();
    }
    public PointModle PiPj(PointModle pi, PointModle pj) { // 構造向量
        PointModle p = new PointModle();
        p.setX(pj.getX() - pi.getX());
        p.setY(pj.getY() - pi.getY());
        return p;
    }
    public double Direction(PointModle pi, PointModle pj, PointModle pk) { // 大於零表示順時針，即右轉，小於零表示逆時針，即左轉，等於零，表示共綫。
        return det(PiPj(pk, pi), PiPj(pj, pi));
    }
    public boolean On_Segment(PointModle pi, PointModle pj, PointModle pk) {
        double max_x = (pi.getX() - pj.getX()) > 0 ? pi.getX() : pj.getX();
        double min_x = (pi.getX() - pj.getX()) < 0 ? pi.getX() : pj.getX();
        double max_y = (pi.getY() - pj.getY()) > 0 ? pi.getY() : pj.getY();
        double min_y = (pi.getY() - pj.getY()) < 0 ? pi.getY() : pj.getY();
        if ((min_x <= pk.getX()) && (pk.getX() <= max_x)
                && (min_y <= pk.getY()) && (pk.getY() <= max_y))
            return true;
        else
            return false;
    }
    public boolean SegmentsIntersect() {
        double d1 = Direction(this.p3, this.p4, this.p1);
        double d2 = Direction(p3, p4, p2);
        double d3 = Direction(p1, p2, p3);
        double d4 = Direction(p1, p2, p4);
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0))
                && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0)))
            return true;
        else if (d1 == 0 && On_Segment(p3, p4, p1))
            return true;
        else if (d2 == 0 && On_Segment(p3, p4, p2))
            return true;
        else if (d3 == 0 && On_Segment(p1, p2, p3))
            return true;
        else if (d4 == 0 && On_Segment(p1, p2, p4))
            return true;
        else
            return false;
    }
}
