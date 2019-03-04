package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/30.
 */

public class SystemMargins {
    private float leftMargin;
    private float rightMargin;

    public float getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(float leftMargin) {
        this.leftMargin = leftMargin;
    }

    public float getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(float rightMargin) {
        this.rightMargin = rightMargin;
    }

    @Override
    public String toString() {
        return "SystemMargins{" +
                "leftMargin=" + leftMargin +
                ", rightMargin=" + rightMargin +
                '}';
    }
}
