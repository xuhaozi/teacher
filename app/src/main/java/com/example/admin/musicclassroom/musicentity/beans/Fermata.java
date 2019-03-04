package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by CY on 2017/7/23.
 */

public class Fermata {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Fermata{" +
                "type='" + type + '\'' +
                '}';
    }
}
