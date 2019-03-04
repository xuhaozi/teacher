package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by CY on 2017/7/23.
 */

public class DirectionType {
    private Wedge wedge;
    private Dynamics dynamics;
    private boolean segno;
    private boolean coda;
    private Words words;

    public Words getWords() {
        return words;
    }

    public void setWords(Words words) {
        this.words = words;
    }

    public boolean isCoda() {
        return coda;
    }

    public void setCoda(boolean coda) {
        this.coda = coda;
    }

    public boolean isSegno() {
        return segno;
    }

    public void setSegno(boolean segno) {
        this.segno = segno;
    }

    public Dynamics getDynamics() {
        return dynamics;
    }


    public void setDynamics(Dynamics dynamics) {
        this.dynamics = dynamics;
    }

    public Wedge getWedge() {
        return wedge;
    }

    public void setWedge(Wedge wedge) {
        this.wedge = wedge;
    }


    @Override
    public String toString() {
        return "DirectionType{" +
                "wedge=" + wedge +
                ", dynamics=" + dynamics +
                ", segno=" + segno +
                ", coda=" + coda +
                ", words=" + words +
                '}';
    }
}
