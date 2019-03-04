package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/30.
 */

public class Time {
    private int beats;
    private int beatType;

    public int getBeats() {
        return beats;
    }

    public void setBeats(int beats) {
        this.beats = beats;
    }

    public int getBeatType() {
        return beatType;
    }

    public void setBeatType(int beatType) {
        this.beatType = beatType;
    }


    @Override
    public String toString() {
        return "Time{" +
                "beats=" + beats +
                ", beatType=" + beatType +
                '}';
    }
}
