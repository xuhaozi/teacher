package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/8/1.
 */

public class Forward {
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Forward{" +
                "duration=" + duration +
                '}';
    }
}
