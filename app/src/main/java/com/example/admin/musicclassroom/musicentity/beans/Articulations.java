package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/8/1.
 */

public class Articulations {
    private boolean accent;
    private boolean staccato;

    public boolean isAccent() {
        return accent;
    }

    public void setAccent(boolean accent) {
        this.accent = accent;
    }

    public boolean isStaccato() {
        return staccato;
    }

    public void setStaccato(boolean staccato) {
        this.staccato = staccato;
    }

    @Override
    public String toString() {
        return "Articulations{" +
                "accent=" + accent +
                ", staccato=" + staccato +
                '}';
    }
}
