package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/7/18.
 */

public class Slur {
    private String type;
    private int number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    @Override
    public String toString() {
        return "Slur{" +
                "type='" + type + '\'' +
                ", number=" + number +
                '}';
    }
}
