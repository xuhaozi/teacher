package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by CY on 2017/7/23.
 */

public class DirectionXY {
    private float directionX;
    private float directionY;


    public float getDirectionX() {
        return directionX;
    }

    public void setDirectionX(float directionX) {
        this.directionX = directionX;
    }

    public float getDirectionY() {
        return directionY;
    }

    public void setDirectionY(float directionY) {
        this.directionY = directionY;
    }


    @Override
    public String
    toString() {
        return "DirectionXY{" +
                "directionX=" + directionX +
                ", directionY=" + directionY +
                '}';
    }
}
