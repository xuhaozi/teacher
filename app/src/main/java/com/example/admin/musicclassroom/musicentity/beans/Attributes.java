package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/30.
 */

public class Attributes {
    private int divisions;
    private Key key;
    private Time time;
    private Clef clef;

    public int getDivisions() {
        return divisions;
    }

    public void setDivisions(int divisions) {
        this.divisions = divisions;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Clef getClef() {
        return clef;
    }

    public void setClef(Clef clef) {
        this.clef = clef;
    }


    @Override
    public String toString() {
        return "Attributes{" +
                "divisions=" + divisions +
                ", key=" + key +
                ", time=" + time +
                ", clef=" + clef +
                '}';
    }
}
