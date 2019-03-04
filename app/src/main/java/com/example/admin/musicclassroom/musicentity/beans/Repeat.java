package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/7/26.
 */

public class Repeat {
    private String direction;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Repeat{" +
                "direction='" + direction + '\'' +
                '}';
    }
}
