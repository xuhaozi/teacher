package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/7/26.
 */

public class BarLine {
    private String location;
    private String barStyle;
    private Repeat repeat;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBarStyle() {
        return barStyle;
    }

    public void setBarStyle(String barStyle) {
        this.barStyle = barStyle;
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }


    @Override
    public String toString() {
        return "BarLine{" +
                "location='" + location + '\'' +
                ", barStyle='" + barStyle + '\'' +
                ", repeat=" + repeat +
                '}';
    }
}
