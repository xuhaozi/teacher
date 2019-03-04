package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Scaling {
    private double millimeters;
    private int tenths;

    public double getMillimeters() {
        return millimeters;
    }

    public void setMillimeters(double millimeters) {
        this.millimeters = millimeters;
    }

    public int getTenths() {
        return tenths;
    }

    public void setTenths(int tenths) {
        this.tenths = tenths;
    }

    @Override
    public String toString() {
        return "Scaling{" +
                "millimeters=" + millimeters +
                ", tenths=" + tenths +
                '}';
    }
}
