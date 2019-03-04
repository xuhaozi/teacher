package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/30.
 */

public class Key {
    private int fifths;

    public int getFifths() {
        return fifths;
    }

    public void setFifths(int fifths) {
        this.fifths = fifths;
    }

    @Override
    public String toString() {
        return "Key{" +
                "fifths=" + fifths +
                '}';
    }
}
