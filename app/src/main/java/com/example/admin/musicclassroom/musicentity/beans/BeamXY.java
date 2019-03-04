package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/7/17.
 */

public class BeamXY {
    private float realX;
    private float realY;


    public float getRealX() {
        return realX;
    }

    public void setRealX(float realX) {
        this.realX = realX;
    }

    public float getRealY() {
        return realY;
    }

    public void setRealY(float realY) {
        this.realY = realY;
    }

    @Override
    public String toString() {
        return "BeamXY{" +
                "realX=" + realX +
                ", realY=" + realY +
                '}';
    }
}
