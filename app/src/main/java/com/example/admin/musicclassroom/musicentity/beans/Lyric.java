package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Lyric {
    private int number;
    private String syllabic;
    private String text;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSyllabic() {
        return syllabic;
    }

    public void setSyllabic(String syllabic) {
        this.syllabic = syllabic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "number=" + number +
                ", syllabic='" + syllabic + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
