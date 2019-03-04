package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/30.
 */

public class SystemLayout {
    private SystemMargins systemMargins;
    private float topSystemDistance;


    public SystemMargins getSystemMargins() {
        return systemMargins;
    }

    public void setSystemMargins(SystemMargins systemMargins) {
        this.systemMargins = systemMargins;
    }

    public float getTopSystemDistance() {
        return topSystemDistance;
    }

    public void setTopSystemDistance(float topSystemDistance) {
        this.topSystemDistance = topSystemDistance;
    }

    @Override
    public String toString() {
        return "SystemLayout{" +
                "systemMargins=" + systemMargins +
                ", topSystemDistance=" + topSystemDistance +
                '}';
    }
}
