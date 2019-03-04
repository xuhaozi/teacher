package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/7/12.
 */

public class Beam {
    private int number;
    private String content;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Beam{" +
                "number=" + number +
                ", content='" + content + '\'' +
                '}';
    }
}
