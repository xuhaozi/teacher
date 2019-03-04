package com.example.admin.musicclassroom.musicentity.beans;


import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Measure {
    private int number;
    private float width;
    private Print print;
    private Attributes attributes;
    private List<Note> notes;
    private List<Direction> direction;
    private BarLine barLine;
    private Forward forward;

    public Forward getForward() {
        return forward;
    }

    public void setForward(Forward forward) {
        this.forward = forward;
    }

    public BarLine getBarLine() {
        return barLine;
    }

    public void setBarLine(BarLine barLine) {
        this.barLine = barLine;
    }

    public List<Direction> getDirection() {
        return direction;
    }

    public void setDirection(List<Direction> direction) {
        this.direction = direction;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Print getPrint() {
        return print;
    }

    public void setPrint(Print print) {
        this.print = print;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "number=" + number +
                ", width=" + width +
                ", print=" + print +
                ", attributes=" + attributes +
                ", notes=" + notes +
                ", direction=" + direction +
                ", barLine=" + barLine +
                ", forward=" + forward +
                '}';
    }
}

