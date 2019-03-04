package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by CY on 2017/7/23.
 */

public class Dot {
    private boolean isDot;


    public boolean isDot() {
        return isDot;
    }

    public void setDot(boolean dot) {
        isDot = dot;
    }

    @Override
    public String toString() {
        return "Dot{" +
                "isDot=" + isDot +
                '}';
    }
}
